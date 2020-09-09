package ro.dascaliucadi.springapp.controller.simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Call;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.CallsServicies;
import ro.dascaliucadi.springapp.simulation_history.CDR;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
import ro.dascaliucadi.springapp.subscription.Subscriptions;

@Controller
public class PhoneCallController {

	private Clients clientOnePhone = null;
	private Clients clientTwoPhone = null;

	private Date dateStart = null;
	private Date dateEnd = null;

	private long minAdd = 0;

	@Autowired
	private final CallsServicies callsServicies;

	@Autowired
	private final ClientsServicies clientServicies;
	
	@Autowired
	private final Extra_ChargesServicies extra_chargesServicies;

	public PhoneCallController(CallsServicies callsServicies, ClientsServicies clientServicies, Extra_ChargesServicies extra_chargesServicies) {
		this.callsServicies = callsServicies;
		this.clientServicies = clientServicies;
		this.extra_chargesServicies = extra_chargesServicies;

	}

	@GetMapping("/simulate/phone-call")
	public String phoneCall(Model model) {
		Clients client = new Clients();

		model.addAttribute("client", client);
		model.addAttribute("clients", clientServicies.findAllClients());

		return "phone_call";
	}
	
	@GetMapping("/history/calls")
	public String historyCalls(Model model) {

		model.addAttribute("calls", callsServicies.getAllCalls());
		return "history_calls";
	}

	@PostMapping("/simulate/phone-call")
	public String phoneCallResponse(@ModelAttribute("client") Clients client) {
		minAdd = client.getCallHistory().getCallMinutes();
		
		client.setCallHistory(new CallsHistory());
		client.getCallHistory().setStartCall();

		String firstNumber = client.getPhone().split(",")[0];
		String secondNumber = client.getPhone().split(",")[1];

		clientOnePhone = clientServicies.findClientByPhone(firstNumber);
		clientOnePhone.setCallHistory(new CallsHistory());
		clientOnePhone.getCallHistory().setStartCall();
		
		clientTwoPhone = clientServicies.findClientByPhone(secondNumber);

		

		if (firstNumber.substring(0, 4).equals(secondNumber.substring(0, 4)))

		{
			System.out.println("Same call furnizor");
			clientOnePhone.getCallHistory().setCallType(String.valueOf(Call.in_network));
		} else {
			System.out.println("Don't same call furnizor");
			clientOnePhone.getCallHistory().setCallType(String.valueOf(Call.outside_network));
		}
		
		clientServicies.updateClient(clientOnePhone);

		return "make_call";
	}

	@PostMapping("/simulate/phone-call/end")
	public String endCall(@ModelAttribute("client") Clients client, Model model) {

		clientOnePhone.getCallHistory().setEndCall();
		try {
			dateStart = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
					.parse(clientOnePhone.getCallHistory().getStartCall());
			dateEnd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(clientOnePhone.getCallHistory().getEndCall());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long minutes = getDateDiff(dateStart, dateEnd, TimeUnit.MINUTES);

		long minSpend = minutes == 0 ? 1 : minutes;
		
		callsServicies.addCall(clientOnePhone, clientTwoPhone.getPhone(),
				clientOnePhone.getCallHistory().getStartCall(), clientOnePhone.getCallHistory().getEndCall(),
				minSpend + minAdd, clientOnePhone.getCallHistory().getCallType());
		
		Clients clientCall = clientServicies.findClientByID(clientOnePhone.getID());
		
		long totalMinutesInNetworkClientCurrent = 0;
		try {
			totalMinutesInNetworkClientCurrent = callsServicies.getTotalMinuteByClientIdAndCallType(clientCall,
					String.valueOf(Call.in_network));
		} catch (Exception e) {

		}
		
		long totalMinutesOutsideNetworkClientCurrent = 0;
		try {
			totalMinutesOutsideNetworkClientCurrent = callsServicies.getTotalMinuteByClientIdAndCallType(clientCall,
					String.valueOf(Call.outside_network));
		} catch (Exception e) {

		}
		
		double callMinutes = 0;
		try {
			callMinutes = clientCall.getSubscription().getMinutesIncluded() - totalMinutesInNetworkClientCurrent;

		} catch (Exception e) {
			callMinutes += clientCall.getSubscription().getMinutesIncluded();
		}
		
		double networkMinutesRemaining = 0;
		try {
			networkMinutesRemaining = clientCall.getSubscription().getNetworkMinutesIncluded()
					- totalMinutesOutsideNetworkClientCurrent;

		} catch (Exception e) {
			networkMinutesRemaining = clientCall.getSubscription().getNetworkMinutesIncluded();
		}
		
		Subscriptions detailSub = new Subscriptions(
				SubscriptionsEnum.valueOf(clientCall.getSubscriptionType() == 1 ? "Standard" : "Premium"));
		
		if (callMinutes > detailSub.getMinutesIncluded()) {
			clientCall.getExtra_charges().setCall(callMinutes - detailSub.getMinutesIncluded());
		}
		
		if (networkMinutesRemaining > detailSub.getNetworkMinutesIncluded()) {
			clientCall.getExtra_charges().setNetworkCall(networkMinutesRemaining - detailSub.getNetworkMinutesIncluded());
		}
		
		Extra_Charges extra_chargesClientCurrent = 
				extra_chargesServicies.getExtraChargesForClientByClientId(clientCall.getID());
		
		double sms = 0;
		try {
			sms = extra_chargesClientCurrent.getSMS();
		} catch(Exception e) { }
		
		double networkSms = 0;
		try {
			networkSms = extra_chargesClientCurrent.getNetworkSMS();
		} catch(Exception e) { }
		
		double internetTraffic = 0;
		try {
			internetTraffic = extra_chargesClientCurrent.getInternetTraffic();
		} catch (Exception e) { }
		
		CDR cdr = new CDR();
		extra_chargesServicies.updateExtra_Charges(
				clientCall.getID(),
				clientCall,
				callMinutes < 0 ? Math.abs(callMinutes) : 0,
				sms / cdr.getPerSms(),
				networkMinutesRemaining < 0 ? Math.abs(networkMinutesRemaining) : 0,
				networkSms / cdr.getPerNetworkSms(),
				internetTraffic / cdr.getPerMbInternetTraffic());
	
		return "homepage";
	}
	
	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
