package ro.dascaliucadi.springapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.security.jwt.AuthenticationRequest;
import ro.dascaliucadi.springapp.security.jwt.AuthenticationResponse;
import ro.dascaliucadi.springapp.security.jwt.MyUserDetailsService;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.token_auth.JwtUtil;

@RequestMapping("/api")
@RestController
public class FirstSpringRestController {

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private  ClientsServicies clientServicies;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
				);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

	@DeleteMapping("client/{id}")
	@ResponseBody
	public String deleteClient(@PathVariable int id) {
		Clients delClient = clientServicies.findClientByID(id);
		clientServicies.deleteClient(delClient);
		return "Client" + id + " deleted";
	}

	@RequestMapping("/clients")
	@ResponseBody
	public List<Clients> getAllClients() {
		return clientServicies.findAllClients();
	}

	@GetMapping("/client/{ID}")
	@ResponseBody
	public Clients getClientById(@PathVariable int ID) {
		return clientServicies.findClientByID(ID);
	}
	
	@PostMapping("/client/id")
	@ResponseStatus(HttpStatus.CREATED)
	public Clients addClient(@RequestBody Clients newClient) {
		return clientServicies.addClient(newClient);
	}
	

	@PutMapping("/client/update")
	@ResponseStatus()
	public Clients updateClient(@RequestBody Clients newClient) {
		return clientServicies.addClient(newClient);
	}
}
