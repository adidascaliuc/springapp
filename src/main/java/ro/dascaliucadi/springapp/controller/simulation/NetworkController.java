package ro.dascaliucadi.springapp.controller.simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.NetworkServicies;
import ro.dascaliucadi.springapp.simulation_history.CDR;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;
import ro.dascaliucadi.springapp.subscription.Subscriptions;

@Controller
public class NetworkController {

	private Clients clientNetwork = null;

	private Date dateStart = null;
	private Date dateEnd = null;

	private long addMB = 0;

	private String networkType = null;

	@Autowired
	private final ClientsServicies clientServicies;

	@Autowired
	private final NetworkServicies networkServicies;
	
	@Autowired 
	private final Extra_ChargesServicies extra_chargesServicies;

	public NetworkController(ClientsServicies clientServicies, NetworkServicies networkServicies, Extra_ChargesServicies extra_chargesServicies) {
		this.clientServicies = clientServicies;
		this.networkServicies = networkServicies;
		this.extra_chargesServicies = extra_chargesServicies;
	}
	
	@GetMapping("/history/network-traffic")
	public String historyTraffic(Model model) {

		model.addAttribute("allNetworkTraffic", networkServicies.getAllTraffic());

		return "history_network";
	}

	@GetMapping("/simulate/network-traffic")
	public String simulateNetworkTraffic(Model model) {
		List<String> netType = new ArrayList<String>();
		netType.add("Read");
		netType.add("Games");
		netType.add("Stream");
		netType.add("Video");

		model.addAttribute("client", new Clients());
		model.addAttribute("netType", netType);
		model.addAttribute("clients", clientServicies.getAllClients());
		return "network_traffic";
	}

	@PostMapping("/simulate/network-traffic")
	public String responseNetworkTraffic(@ModelAttribute("client") Clients client) {
		addMB = client.getNetworkHistory().getMBSpend();
		networkType =  client.getNetworkHistory().getTrafficType();
		client.setNetworkHistory(new NetworkHistory());

		client.getNetworkHistory().setTrafficStart();
		
		String phoneNumber = client.getPhone().split(",")[0];
		
		clientNetwork = clientServicies.findClientByPhone(phoneNumber);
		clientNetwork.setNetworkHistory(new NetworkHistory());
		clientNetwork.getNetworkHistory().setTrafficType(client.getNetworkHistory().getTrafficType());
		clientNetwork.getNetworkHistory().setTrafficStart();

		clientServicies.updateClient(clientNetwork);
		return "turnOn_networkTraffic";
	}

	@PostMapping("simulate/network-traffic/end")
	public String endNetwork(Model model) {

		clientNetwork.getNetworkHistory().setTrafficEnd();

		try {
			dateStart = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
					.parse(clientNetwork.getNetworkHistory().getTrafficStart());
			dateEnd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
					.parse(clientNetwork.getNetworkHistory().getTrafficEnd());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long minutes = getDateDiff(dateStart, dateEnd, TimeUnit.MINUTES);

		long minSpend = minutes == 0 ? 1 : minutes;

		networkServicies.addNetwork(clientNetwork, clientNetwork.getNetworkHistory().getTrafficStart(),
				clientNetwork.getNetworkHistory().getTrafficEnd(), minSpend + addMB, networkType);
		
		
		clientServicies.updateClient(clientNetwork);
		
		Clients clientCurrentNetwork = clientServicies.findClientByID(clientNetwork.getID());
		
		long totalMbClientCurrent = 0;
		try {
			totalMbClientCurrent = networkServicies.getTotalMbByClient(clientCurrentNetwork);
		} catch (Exception e) {

		}
		
		double trafficRemaining = 0;
		try {
			trafficRemaining = clientCurrentNetwork.getSubscription().getTrafficIncluded() - totalMbClientCurrent;

		} catch (Exception e) {
			trafficRemaining = clientCurrentNetwork.getSubscription().getTrafficIncluded();
		}
		
		Subscriptions detailSub = new Subscriptions(
				SubscriptionsEnum.valueOf(clientCurrentNetwork.getSubscriptionType() == 1 ? "Standard" : "Premium"));
		
		if (trafficRemaining > detailSub.getTrafficIncluded()) {
			clientCurrentNetwork.getExtra_charges().setInternetTraffic(trafficRemaining - detailSub.getTrafficIncluded());
		}
		
		Extra_Charges extra_chargesClientCurrent = 
				extra_chargesServicies.getExtraChargesForClientByClientId(clientCurrentNetwork.getID());
		
		double call = 0;
		try {
			call = extra_chargesClientCurrent.getCall();
		} catch (Exception e) { }
		
		double sms = 0;
		try {
			sms = extra_chargesClientCurrent.getSMS();
		} catch (Exception e) { }
		
		double networkCall = 0;
		try {
			networkCall = extra_chargesClientCurrent.getNetworkCall();
		} catch (Exception e) { }
		
		double networkSms = 0;
		try {
			networkSms = extra_chargesClientCurrent.getNetworkSMS();
		} catch (Exception e) { }
		CDR cdr = new CDR();
		extra_chargesServicies.updateExtra_Charges(
				clientCurrentNetwork.getID(),
				clientCurrentNetwork,
				call / cdr.getPerCallMinute(),
				sms / cdr.getPerSms(),
				networkCall / cdr.getPerNetworkCallMinute(),
				networkSms / cdr.getPerNetworkSms(),
				trafficRemaining < 0 ? Math.abs(trafficRemaining) : 0);
		
		
		return "homepage";
	}

	
	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
