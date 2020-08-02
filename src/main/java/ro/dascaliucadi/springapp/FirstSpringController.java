package ro.dascaliucadi.springapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.servicies.ClientServicies;


@RestController
public class FirstSpringController {
	
	@Autowired
	private final ClientServicies clientServicies;
	
	public FirstSpringController(ClientServicies clientServicies) {
		this.clientServicies = clientServicies;
	}
	
	@DeleteMapping("client/delete/{id}")
	public String deleteClient(@PathVariable int id) {
		Client delClient = clientServicies.findClientByID(id);
		clientServicies.deleteClient(delClient);
		return "Client" +id+" deleted";
	}
	
	@RequestMapping("/")
	public String hello() {
		return "index";
	}
	
	@GetMapping(path="/accounts/id")
	public List<Client> getAllClients() {
		return clientServicies.findAllClients();
	}
	
	@GetMapping("/client/{ID}")
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
	
}