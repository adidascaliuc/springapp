package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ro.dascaliucadi.springapp.servicies.simulation_history.CDRServicies;

@Controller
public class CDRController {

	@Autowired
	private CDRServicies cdrServicies;
	
	@GetMapping("/table/cdr")
	public String cdr(Model model) {
		
		model.addAttribute("cdrs", cdrServicies.getAllCdrs());
		
		return "cdr.html";
	}
}
