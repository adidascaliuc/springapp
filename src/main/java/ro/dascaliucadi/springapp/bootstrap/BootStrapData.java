package ro.dascaliucadi.springapp.bootstrap;

import java.security.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.client.ClientRepository;
import ro.dascaliucadi.springapp.enumerari.Extra_ChargesEnum;
import ro.dascaliucadi.springapp.enumerari.SubscriptionEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.subscription.Subscription;
import ro.dascaliucadi.springapp.subscription.SubscriptionRepository;

@Component
public class BootStrapData implements CommandLineRunner {

	private Subscription s1, s2, s3;
	private Extra_Charges e1, e2, e3;
	
	private final ClientRepository clientRepository;
	private final Extra_ChargesRepository extra_chargesRepository;
	private final SubscriptionRepository subscriptionRepository;
	
	public BootStrapData(ClientRepository clientRepository, Extra_ChargesRepository extra_chargesRepository, SubscriptionRepository subscriptionRepository) {
		this.clientRepository = clientRepository;
		this.extra_chargesRepository = extra_chargesRepository;
		this.subscriptionRepository = subscriptionRepository;
		
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Loading Clients data...");
		
		Client c1 = new Client("Dascaliuc Adi", "Suceava", 789, "07543887339");
		clientRepository.save(c1);
		
		s1 = new Subscription(SubscriptionEnum.standard, c1);
		subscriptionRepository.save(s1);
		
		e1 = new Extra_Charges(Extra_ChargesEnum.standard ,c1);
		extra_chargesRepository.save(e1);
		
		
		Client c2 = new Client("Dacian Ciolos", "Bucuresti", 9999.99, "0125869730");
		clientRepository.save(c2);
		
		s2 = new Subscription(SubscriptionEnum.premium, c2);
		subscriptionRepository.save(s2);
		
		e2 = new Extra_Charges(Extra_ChargesEnum.premium, c2);
		extra_chargesRepository.save(e2);
		
		
		Client c3 = new Client("Tepes Vlad", "Cluj", 234.34, "0321685963");
		clientRepository.save(c3);
		
		
		s3 = new Subscription(SubscriptionEnum.standard, c3);
		subscriptionRepository.save(s3);
		
		e3 = new Extra_Charges(Extra_ChargesEnum.premium ,c3);
		extra_chargesRepository.save(e3);
		
		System.out.println("Loaded " + clientRepository.count() + " clients.");
		
	}
}
