package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Call;
import ro.dascaliucadi.springapp.enumerari.Sms;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.CallsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.NetworkServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.SmsServicies;
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

	private Subscriptions detailSub;

	public ChargerController(ClientsServicies clientServicies, CallsServicies callsServicies, SmsServicies smsServicies,
			NetworkServicies networkServicies) {
		this.clientServicies = clientServicies;
		this.callsServicies = callsServicies;
		this.smsServicies = smsServicies;
		this.networkServicies = networkServicies;

	}

	@GetMapping("/simulate")
	public String simulate() {
		return "simulate_charger";
	}

	@PostMapping("/cronos")
	public String cronos(@ModelAttribute("client") Clients clientModel, Model model) {
		Clients client = clientServicies.findClientByPhone(clientModel.getPhone());
		detailSub = new Subscriptions(
				SubscriptionsEnum.valueOf(client.getSubscriptionType() == 1 ? "Standard" : "Premium"));

		double callMinutes = 0;
		double smsRemaining = 0;
		double networkMinutesRemaining = 0;
		double networkSmsRemaining = 0;
		double trafficRemaining = 0;
		double subscriptionPay = client.getSubscription().getMonthlyCost();

		try {
			callMinutes = client.getSubscription().getMinutesIncluded() - 
					callsServicies.getTotalMinuteByClientIdAndCallType(client, String.valueOf(Call.in_network));

		} catch (Exception e) {
			callMinutes += client.getSubscription().getMinutesIncluded();
		}

		try {
			networkMinutesRemaining = client.getSubscription().getNetworkMinutesIncluded()
					- callsServicies.getTotalMinuteByClientIdAndCallType(client, String.valueOf(Call.outside_network));;

		} catch (Exception e) {
			networkMinutesRemaining = client.getSubscription().getNetworkMinutesIncluded();
		}

		try {
			smsRemaining = client.getSubscription().getSMSIncluded() 
					- smsServicies.getTotalSentSmsByCliendIdAndSmsType(client, String.valueOf(Sms.in_network));

		} catch (Exception e) {
			smsRemaining += client.getSubscription().getSMSIncluded();
		}

		try {
			networkSmsRemaining = client.getSubscription().getNetworkSMSIncluded()
					- smsServicies.getTotalSentSmsByCliendIdAndSmsType(client, String.valueOf(Sms.outside_network));;

		} catch (Exception e) {
			networkSmsRemaining = client.getSubscription().getNetworkSMSIncluded();
		}

		try {
			trafficRemaining = client.getSubscription().getTrafficIncluded() 
					- networkServicies.getTotalMbByClient(client);

		} catch (Exception e) {
			trafficRemaining = client.getSubscription().getTrafficIncluded();
		}

		// Calculate extra charges
		if (callMinutes > detailSub.getMinutesIncluded()) {
			client.getExtra_charges().setCall(callMinutes - detailSub.getMinutesIncluded());
		}

		if (smsRemaining > detailSub.getSMSIncluded()) {
			client.getExtra_charges().setSMS(smsRemaining - detailSub.getSMSIncluded());
		}

		if (networkMinutesRemaining > detailSub.getNetworkMinutesIncluded()) {
			client.getExtra_charges().setNetworkCall(networkMinutesRemaining - detailSub.getNetworkMinutesIncluded());
		}

		if (networkSmsRemaining > detailSub.getNetworkSMSIncluded()) {
			client.getExtra_charges().setNetworkSMS(networkSmsRemaining - detailSub.getNetworkSMSIncluded());
		}

		if (trafficRemaining > detailSub.getTrafficIncluded()) {
			client.getExtra_charges().setInternetTraffic(trafficRemaining - detailSub.getTrafficIncluded());
		}

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