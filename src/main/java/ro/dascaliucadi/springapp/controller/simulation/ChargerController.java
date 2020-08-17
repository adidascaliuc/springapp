package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Call;
import ro.dascaliucadi.springapp.enumerari.Sms;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.CallsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.SmsServicies;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;

@Controller
public class ChargerController {

	@Autowired
	private final ClientsServicies clientServicies;

	@Autowired
	private final CallsServicies callsServicies;

	@Autowired
	private final SmsServicies smsServicies;

	public ChargerController(ClientsServicies clientServicies, CallsServicies callsServicies,
			SmsServicies smsServicies) {
		this.clientServicies = clientServicies;
		this.callsServicies = callsServicies;
		this.smsServicies = smsServicies;

	}

	@GetMapping("/simulate")
	public String simulate() {
		return "simulate_charger";
	}
	
	@GetMapping("/invoice/pdf")
	public String invoicePdf() {
		return "";
	}

	@GetMapping("/cronos/{id}")
	public String cronos(@PathVariable int id, Model model) {
		Clients client = clientServicies.findClientByID(id);

		CallsHistory call = callsServicies.getCallByClientId(client.getID());
		SmsHistory sms = smsServicies.getSmsByClientId(client.getID());

		double callMinutes;
		double smsRemaining;
		double networkMinutesRemaining;
		double networkSmsRemaining;

		double trafficRemaining = client.getSubscription().getTrafficIncluded();
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