package ro.dascaliucadi.springapp.controller.simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.NetworkServicies;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;

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

	public NetworkController(ClientsServicies clientServicies, NetworkServicies networkServicies) {
		this.clientServicies = clientServicies;
		this.networkServicies = networkServicies;
	}

	@PostMapping("/simulate/network-traffic")
	public String responseNetworkTraffic(@ModelAttribute("client") Clients client) {
		addMB = client.getNetworkHistory().getMinutesSpend();
		
		client.setNetworkHistory(new NetworkHistory());

		client.getNetworkHistory().setTrafficStart();
		
		String phoneNumber = client.getPhone().split(",")[0];

		clientNetwork = clientServicies.findClientByPhone(phoneNumber);
		clientNetwork.setNetworkHistory(new NetworkHistory());
		clientNetwork.getNetworkHistory().setTrafficStart();

		return "turnOn_networkTraffic";
	}

	@PostMapping("simulate/network-traffic/end")
	public String endNetwork() {

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
		return "homepage";
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

	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
