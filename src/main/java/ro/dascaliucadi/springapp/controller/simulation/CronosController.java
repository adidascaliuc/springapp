package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;

@Controller
public class CronosController {
	
	@Autowired
	ClientsServicies clientServicies;
	
	@GetMapping("/cronos")
	public String invoice(Model model) {
		
		model.addAttribute("client", new Clients());
		model.addAttribute("clients", clientServicies.getAllClients());
		return "cronosPhone";
	}
}
