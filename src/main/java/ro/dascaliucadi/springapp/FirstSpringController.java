package ro.dascaliucadi.springapp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.subscription.SubscriptionServicies;

@Controller
public class FirstSpringController {
	
	@Autowired
	private final ClientsServicies clientServicies;
	@Autowired
	private final SubscriptionServicies subscriptionServicies;
	@Autowired
	private final Extra_ChargesServicies extra_chargesServicies;
	
	public FirstSpringController(ClientsServicies clientServicies, SubscriptionServicies subscriptionServicies, Extra_ChargesServicies extra_chargesServicies) {
		this.clientServicies = clientServicies;
		this.subscriptionServicies = subscriptionServicies;
		this.extra_chargesServicies = extra_chargesServicies;
		
	}

	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("clients", clientServicies.findAllClients());
		return "clients";
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
	
	@GetMapping("/modify/client/")
	public String modifyForm(Model model, @PathVariable int id) {
		model.addAttribute("clients", clientServicies.findAllClients());
		return "modify";
	}
	
	@RequestMapping(value="/succesfful", method=RequestMethod.POST)
	public String succesfful(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("balance") double balance, @RequestParam("subscriptionCkbx") String subType,
			@RequestParam("extra_chargesCkbx") String extraType, HttpServletRequest request) {
		
		request.setAttribute("msg", id+" "+name+" "+address+" "+phone+" "+balance+" "+subType+" "+extraType);
		clientServicies.updateClient(id, name, address, phone, balance, subType, extraType);
		return "test";
	}
	
}