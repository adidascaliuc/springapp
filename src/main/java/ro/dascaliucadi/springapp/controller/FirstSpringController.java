package ro.dascaliucadi.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.subscription.SubscriptionServicies;

@Controller
@ComponentScan({ "ro.dascaliucadi.springapp" })
@EntityScan("ro.dascaliucadi.springapp")
@EnableJpaRepositories("ro.dascaliucadi")
public class FirstSpringController {

	@Autowired
	private final ClientsServicies clientServicies;
	@Autowired
	private final SubscriptionServicies subscriptionServicies;
	@Autowired
	private final Extra_ChargesServicies extra_chargesServicies;

	public FirstSpringController(ClientsServicies clientServicies, SubscriptionServicies subscriptionServicies,
			Extra_ChargesServicies extra_chargesServicies) {
		this.clientServicies = clientServicies;
		this.subscriptionServicies = subscriptionServicies;
		this.extra_chargesServicies = extra_chargesServicies;

	}

	@GetMapping("/")
	public String mainPage() {
		return "homepage";
	}

	@GetMapping("/table/clients")
	public String welcome(Model model) {
		model.addAttribute("clients", clientServicies.findAllClients());
		return "clients";
	}

	@GetMapping("/table/subscriptions")
	public String showAllSubscriptions(Model model) {
		model.addAttribute("subscriptions", subscriptionServicies.getAllSubscriptions());
		return "subscriptions";
	}

	@GetMapping("/table/extra_charges")
	public String showAllExtra_Chargeses(Model model) {
		model.addAttribute("extra_charges", extra_chargesServicies.getAllExtra_Charges());
		return "extra_charges";
	}

	@GetMapping("modify/client")
	public String modifyClient(@RequestParam int id, Model model) {
		
		model.addAttribute("client", new Clients());
		model.addAttribute("client", clientServicies.findClientByID(id));
		
		return "modify";
	}
	
	@PostMapping("modify/client")
	public String modifyDone(@ModelAttribute("client") Clients client) {
		
		clientServicies.updateClient(client);
		
		return "homepage";
	}
	
	@GetMapping("delete/client")
	public String delteClient(@RequestParam int id) {
		
		clientServicies.deleteClient(clientServicies.findClientByID(id));
		
		return "homepage";
	}
	
	@GetMapping("/add/client")
	public String addClient(Model model) {
		
		model.addAttribute("client", new Clients());
		
		//return "addClient";
		return "test";
	}
	
	@PostMapping("/add/client")
	public String registerClient(@ModelAttribute("client") Clients client) {
		
		clientServicies.addClient(client);
		
		return "homepage";
	}

}