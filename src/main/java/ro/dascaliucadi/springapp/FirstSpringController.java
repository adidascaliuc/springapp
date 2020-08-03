package ro.dascaliucadi.springapp;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.servicies.client.ClientServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.subscription.SubscriptionServicies;


@Controller
public class FirstSpringController {
	
	@Autowired
	private final ClientServicies clientServicies;
	@Autowired
	private final SubscriptionServicies subscriptionServicies;
	@Autowired
	private final Extra_ChargesServicies extra_chargesServicies;
	
	public FirstSpringController(ClientServicies clientServicies, SubscriptionServicies subscriptionServicies, Extra_ChargesServicies extra_chargesServicies) {
		this.clientServicies = clientServicies;
		this.subscriptionServicies = subscriptionServicies;
		this.extra_chargesServicies = extra_chargesServicies;
		
	}
	
	
	//@Value("${welcome.message:test}") //like this you can set a message
	private String message = "Hello World"; //or you can put it in a String and ->***

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message); // ->*** user here
		return "welcome";
	}
	
	
	@DeleteMapping("client/delete/{id}")
	@ResponseBody
	public String deleteClient(@PathVariable int id) {
		Client delClient = clientServicies.findClientByID(id);
		clientServicies.deleteClient(delClient);
		return "Client" +id+" deleted";
	}
	
	@GetMapping("/accounts/id")
	@ResponseBody
	public List<Client> getAllClients() {
		return clientServicies.findAllClients();
	}
	
	@GetMapping("/client/{ID}")
	@ResponseBody
	public Client getClientById(@PathVariable int ID) {
		return  clientServicies.findClientByID(ID);
	}
		
	@PostMapping("/accounts/id")
	@ResponseStatus(HttpStatus.CREATED)
	public Client addClient(@RequestBody Client newClient) {
		return clientServicies.addClient(newClient);
	}
	
	@PutMapping("/client/update")
	@ResponseStatus(HttpStatus.CREATED)
	public Client updateClient(@RequestBody Client newClient) {
		return clientServicies.addClient(newClient);
	}
	
	@GetMapping("/table-accounts")
	public String showAllCliens(HttpServletRequest request) {
		request.setAttribute("clients", clientServicies.getAllClients());
		request.setAttribute("mode", "ALL_CLIENTS");
		return "welcome";
	}
	
	@GetMapping("/table-subscriptions")
	public String showAllSubscriptions(HttpServletRequest request) {
		request.setAttribute("mode", "ALL_SUBSCRIPTIONS");
		request.setAttribute("subscriptions", subscriptionServicies.getAllSubscriptions());
		return "welcome";
	}
	
	@GetMapping("/table-extra_charges")
	public String showAllExtra_Chargeses(HttpServletRequest request) {
		request.setAttribute("mode", "ALL_EXTRACHARGESES");
		request.setAttribute("extra_chargeses", extra_chargesServicies.getAllExtra_Charges());
		return "welcome";
	}
	
}