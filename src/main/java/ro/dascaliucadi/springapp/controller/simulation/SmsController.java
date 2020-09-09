package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Sms;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.SmsServicies;
import ro.dascaliucadi.springapp.simulation_history.CDR;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;
import ro.dascaliucadi.springapp.subscription.Subscriptions;

@Controller
public class SmsController {

	private Clients clientOneSms = null;
	private Clients clientTwoSms = null;
	
	private int addSms = 0;

	@Autowired
	private final ClientsServicies clientServicies;

	@Autowired
	private final SmsServicies smsServicies;
	
	@Autowired
	private final Extra_ChargesServicies extra_chargesServicies;

	public SmsController(ClientsServicies clientServicies, SmsServicies smsServicies, Extra_ChargesServicies extra_chargesServicies) {
		this.clientServicies = clientServicies;
		this.smsServicies = smsServicies;
		this.extra_chargesServicies = extra_chargesServicies;
	}

	@GetMapping("/simulate/message")
	public String simulateMessage(Model model) {
		Clients client = new Clients();

		model.addAttribute("client", client);
		model.addAttribute("clients", clientServicies.findAllClients());

		return "message";
	}

	@PostMapping("/simulate/message")
	public String messageResponse(@ModelAttribute("client") Clients client, Model model) {

		addSms = client.getSmsHistory().getNrOfSms();
		
		String firstNumber = client.getPhone().split(",")[0];
		String secondNumber = client.getPhone().split(",")[1];
		
		clientOneSms = clientServicies.findClientByPhone(firstNumber);
		clientOneSms.setSmsHistory(new SmsHistory());
		
		if (firstNumber.substring(0, 4).equals(secondNumber.substring(0, 4))) {
			clientOneSms.getSmsHistory().setSmsType(String.valueOf(Sms.in_network));
		} else {
			clientOneSms.getSmsHistory().setSmsType(String.valueOf(Sms.outside_network));
		}

		clientOneSms = clientServicies.findClientByPhone(firstNumber);
		clientTwoSms = clientServicies.findClientByPhone(secondNumber);

		clientOneSms.getSmsHistory().setDateSmsSent();

		SmsHistory smsClientCurrent = new SmsHistory();
		for (SmsHistory sms : smsServicies.getAllSms()) {
			if (sms.getClientId() == clientOneSms.getID()) {
				smsClientCurrent = sms;
				break;
			}
		}
		smsClientCurrent.setNrOfSms(1);

		smsServicies.addSms(clientOneSms, clientTwoSms.getPhone(), clientOneSms.getSmsHistory().getDateSmsSent(),
				smsClientCurrent.getNrOfSms() + addSms, clientOneSms.getSmsHistory().getSmsType());
	
		Clients clientCurrentSms = clientServicies.findClientByID(clientOneSms.getID());
		
		int totalSmsInNetworkClientCurrent = 0;
		try {
			totalSmsInNetworkClientCurrent = smsServicies.getTotalSentSmsByCliendIdAndSmsType(clientCurrentSms,
					String.valueOf(Sms.in_network));
		} catch (Exception e) {

		}

		int totalSmsOutsideNetworkClientCurrent = 0;
		try {
			totalSmsOutsideNetworkClientCurrent = smsServicies.getTotalSentSmsByCliendIdAndSmsType(clientCurrentSms,
					String.valueOf(Sms.outside_network));
		} catch (Exception e) {

		}
		
		double smsRemaining = 0;
		try {
			smsRemaining = clientCurrentSms.getSubscription().getSMSIncluded() - totalSmsInNetworkClientCurrent;

		} catch (Exception e) {
			smsRemaining += clientCurrentSms.getSubscription().getSMSIncluded();
		}

		double networkSmsRemaining = 0;
		try {
			networkSmsRemaining = clientCurrentSms.getSubscription().getNetworkSMSIncluded()
					- totalSmsOutsideNetworkClientCurrent;

		} catch (Exception e) {
			networkSmsRemaining = clientCurrentSms.getSubscription().getNetworkSMSIncluded();
		}
		
		
		Subscriptions detailSub = new Subscriptions(
				SubscriptionsEnum.valueOf(clientCurrentSms.getSubscriptionType() == 1 ? "Standard" : "Premium"));
		
		if (smsRemaining > detailSub.getSMSIncluded()) {
			clientCurrentSms.getExtra_charges().setSMS(smsRemaining - detailSub.getSMSIncluded());
		}
		
		if (networkSmsRemaining > detailSub.getNetworkSMSIncluded()) {
			clientCurrentSms.getExtra_charges().setNetworkSMS(networkSmsRemaining - detailSub.getNetworkSMSIncluded());
		}
		
		Extra_Charges extra_chargesClientCurrent = 
				extra_chargesServicies.getExtraChargesForClientByClientId(clientCurrentSms.getID());
		
		double call = 0;
		try {
			call = extra_chargesClientCurrent.getCall();
		} catch (Exception e) { }
		
		double networkCall = 0;
		try {
			networkCall = extra_chargesClientCurrent.getNetworkCall();
		} catch (Exception e) { }
		
		double internetTraffic = 0;
		try {
			internetTraffic = extra_chargesClientCurrent.getInternetTraffic();
		} catch (Exception e) { }
		
		CDR cdr = new CDR();
		
		extra_chargesServicies.updateExtra_Charges(
				clientCurrentSms.getID(),	
				clientCurrentSms,
				call / cdr.getPerCallMinute(),
				smsRemaining < 0 ? Math.abs(smsRemaining) : 0,
				networkCall / cdr.getPerNetworkCallMinute(),
				networkSmsRemaining < 0 ? Math.abs(networkSmsRemaining) : 0,
				internetTraffic / cdr.getPerMbInternetTraffic());
		
		return "homepage";
	}

	@GetMapping("/history/message")
	public String historyMessage(Model model) {

		model.addAttribute("allSms", smsServicies.getAllSms());

		return "history_message";
	}

}
