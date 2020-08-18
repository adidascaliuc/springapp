package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Call;
import ro.dascaliucadi.springapp.enumerari.Sms;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.enumerari.Trafic;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.CallsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.NetworkServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.SmsServicies;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;
import ro.dascaliucadi.springapp.subscription.Subscriptions;

@Controller
public class ChargerController {

	@Autowired
	private final ClientsServicies clientServicies;

	@Autowired
	private final CallsServicies callsServicies;

	@Autowired
	private final SmsServicies smsServicies;
	
	@Autowired
	private final NetworkServicies networkServicies;
	
	@Autowired
	private Extra_ChargesServicies extra_chargesServicies;

	private Subscriptions detailSub;

	public ChargerController(ClientsServicies clientServicies, CallsServicies callsServicies,
			SmsServicies smsServicies, NetworkServicies networkServicies) {
		this.clientServicies = clientServicies;
		this.callsServicies = callsServicies;
		this.smsServicies = smsServicies;
		this.networkServicies = networkServicies;

	}

	@GetMapping("/simulate")
	public String simulate() {
		return "simulate_charger";
	}

	@GetMapping("/cronos/{id}")
	public String cronos(@PathVariable int id, Model model) {
		Clients client = clientServicies.findClientByID(id);

		detailSub = new Subscriptions(SubscriptionsEnum.valueOf(client.getSubscriptionType() == 1?"Standard":"Premium"));
		CallsHistory call = callsServicies.getCallByClientId(client.getID());
		SmsHistory sms = smsServicies.getSmsByClientId(client.getID());
		NetworkHistory net = networkServicies.getNetworkByClientId(id);

		double callMinutes;
		double smsRemaining;
		double networkMinutesRemaining;
		double networkSmsRemaining;

		double trafficRemaining;
		double subscriptionPay = client.getSubscription().getMonthlyCost();

		try {
			callMinutes = (call.getCallType().equals(String.valueOf(Call.in_network)))
					? client.getSubscription().getMinutesIncluded() - call.getCallMinutes()
					: client.getSubscription().getMinutesIncluded();

			client.getSubscription().setMinutesIncluded(callMinutes);

		} catch (Exception e) {
			callMinutes = client.getSubscription().getMinutesIncluded();
			client.getSubscription().setMinutesIncluded(callMinutes);
		}

		try {
			networkMinutesRemaining = (call.getCallType().equals(String.valueOf(Call.outside_network)))
					? client.getSubscription().getNetworkMinutesIncluded() - call.getCallMinutes()
					: client.getSubscription().getNetworkMinutesIncluded();
			client.getSubscription().setNetworkMinutesIncluded(networkMinutesRemaining);

		} catch (Exception e) {
			networkMinutesRemaining = client.getSubscription().getNetworkMinutesIncluded();
			client.getSubscription().setNetworkMinutesIncluded(networkMinutesRemaining);
		}

		try {
			smsRemaining = (sms.getSmsType().equals(String.valueOf(Sms.in_network)))
					? client.getSubscription().getSMSIncluded() - sms.getNrOfSms()
					: client.getSubscription().getSMSIncluded();
			client.getSubscription().setSMSIncluded(smsRemaining);

		} catch (Exception e) {
			smsRemaining = client.getSubscription().getSMSIncluded();
			client.getSubscription().setSMSIncluded(smsRemaining);
		}

		try {
			networkSmsRemaining = (sms.getSmsType().equals(String.valueOf(Sms.outside_network)))
					? client.getSubscription().getNetworkSMSIncluded() - sms.getNrOfSms()
					: client.getSubscription().getNetworkSMSIncluded();
			client.getSubscription().setNetworkSMSIncluded(networkSmsRemaining);

		} catch (Exception e) {
			networkSmsRemaining = client.getSubscription().getNetworkSMSIncluded();
			client.getSubscription().setNetworkSMSIncluded(networkSmsRemaining);
		}
		
		try {
			trafficRemaining = (net.getTrafficType().equals(String.valueOf(Trafic.Read)))
					? client.getSubscription().getTrafficIncluded() - net.getMinutesSpend()
					: client.getSubscription().getTrafficIncluded();
			client.getSubscription().setTrafficIncluded(trafficRemaining);
			
		} catch (Exception e) {
			trafficRemaining = client.getSubscription().getTrafficIncluded();
			client.getSubscription().setTrafficIncluded(trafficRemaining);
		}

		// Calculate extra charges
		//callMinutes = Math.abs(callMinutes);
		if (callMinutes > detailSub.getMinutesIncluded()) {
			client.getExtra_charges().setCall(callMinutes - detailSub.getMinutesIncluded());
		}

		//smsRemaining = Math.abs(smsRemaining);
		if (smsRemaining > detailSub.getSMSIncluded()) {
			client.getExtra_charges().setSMS(smsRemaining - detailSub.getSMSIncluded());
		}

		//networkMinutesRemaining = Math.abs(networkMinutesRemaining);
		if (networkMinutesRemaining > detailSub.getNetworkMinutesIncluded()) {
			client.getExtra_charges().setNetworkCall(networkMinutesRemaining - detailSub.getNetworkMinutesIncluded());
		}
		
		//networkSmsRemaining = Math.abs(networkSmsRemaining);
		if (networkSmsRemaining > detailSub.getNetworkSMSIncluded()) {
			client.getExtra_charges().setNetworkSMS(networkSmsRemaining - detailSub.getNetworkSMSIncluded());
		}
		
		//trafficRemaining = Math.abs(trafficRemaining);
		if (trafficRemaining > detailSub.getTrafficIncluded()) {
			client.getExtra_charges().setInternetTraffic(trafficRemaining - detailSub.getTrafficIncluded());
		}

		extra_chargesServicies.addExtra_Charges(client,
				callMinutes < 0?callMinutes:0,
				smsRemaining < 0?smsRemaining:0,
				networkMinutesRemaining < 0?networkMinutesRemaining:0,
				networkSmsRemaining < 0?networkSmsRemaining:0,
				trafficRemaining < 0?trafficRemaining:0 );
		
		model.addAttribute("callMinutes", callMinutes);
		model.addAttribute("smsRemaining", smsRemaining);
		model.addAttribute("networkMinutesRemaining", networkMinutesRemaining);
		model.addAttribute("networkSmsRemaining", networkSmsRemaining);
		model.addAttribute("trafficRemaining", trafficRemaining);
		model.addAttribute("subscriptionPay", subscriptionPay);
		model.addAttribute("subType", client.getSubscription().getSubscriptionType());

		return "cronos";
	}
}