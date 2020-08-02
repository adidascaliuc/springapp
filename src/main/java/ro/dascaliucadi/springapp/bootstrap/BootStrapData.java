package ro.dascaliucadi.springapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.client.ClientRepository;

@Component
public class BootStrapData implements CommandLineRunner {

	private final ClientRepository clientRepository;
	
	public BootStrapData(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
		
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Loading Clients data...");
		
		Client c1 = new Client();
		c1.setID(1);
		c1.setName("Dascaliuc Adi");
		c1.setAddress("Suceava");
		c1.setBalance(300.56);
		c1.setPhone("0743887339");
		clientRepository.save(c1);
		
		Client c2 = new Client();
		c2.setID(2);
		c2.setName("Mircescu Mircea");
		c2.setAddress("Bucuresti");
		c2.setBalance(56.7);
		c2.setPhone("1234567890");
		clientRepository.save(c2);
		
		Client c3 = new Client();
		c3.setID(3);
		c3.setName("Ionescu Ion");
		c3.setAddress("Cluj");
		c3.setBalance(234.55);
		c3.setPhone("9876543210");
		clientRepository.save(c3);
		
		System.out.println("Loaded " + clientRepository.count() + " clients.");
		
	}
}
