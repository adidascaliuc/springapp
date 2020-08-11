package ro.dascaliucadi.springapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.clients.ClientsRepository;
import ro.dascaliucadi.springapp.enumerari.Extra_ChargesEnum;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.subscription.Subscriptions;
import ro.dascaliucadi.springapp.subscription.SubscriptionsRepository;

@Component
public class BootStrapData implements CommandLineRunner {

	private Subscriptions s1, s2;
	private Extra_Charges e1, e2;
	private Clients c1, c2, c3;
	
	private final ClientsRepository clientRepository;
	private final Extra_ChargesRepository extra_chargesRepository;
	private final SubscriptionsRepository subscriptionsRepository;
	
	public BootStrapData(ClientsRepository clientRepository, Extra_ChargesRepository extra_chargesRepository, SubscriptionsRepository subscriptionsRepository) {
		this.clientRepository = clientRepository;
		this.extra_chargesRepository = extra_chargesRepository;
		this.subscriptionsRepository = subscriptionsRepository;
		
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Loading Clients data...");
		
		s1 = new Subscriptions(SubscriptionsEnum.standard);
		s1.setID(1);
		subscriptionsRepository.save(s1);
		
		s2 = new Subscriptions(SubscriptionsEnum.premium);
		s2.setID(2);
		subscriptionsRepository.save(s2);
		
		
		e1 = new Extra_Charges(Extra_ChargesEnum.standard);
		e1.setID(1);
		extra_chargesRepository.save(e1);
		
		e2 = new Extra_Charges(Extra_ChargesEnum.premium);
		e2.setID(2);
		extra_chargesRepository.save(e2);
		
		
		c1 = new Clients("Dascaliuc Adi", "Suceava", 800.78, "0743887339");
		c1.setSubscriptionType(s1.getSubscriptionType());
		c1.setExtra_ChargesType(e1.getExtra_ChargesType());
		clientRepository.save(c1);
		
		c2 = new Clients("Vasilescu Vasile", "Cluj", 777.77, "0213698574");
		c2.setSubscriptionType(s2.getSubscriptionType());
		c2.setExtra_ChargesType(e2.getExtra_ChargesType());
		clientRepository.save(c2);
		
		c3 = new Clients("Stefanescu Stefan", "Brasov", 34.56, "0216983574");
		c3.setSubscriptionType(s1.getSubscriptionType());
		c3.setExtra_ChargesType(e2.getExtra_ChargesType());
		clientRepository.save(c3);
		
		System.out.println("Loaded " + subscriptionsRepository.count() + " subscriptions.");
		System.out.println("Loaded " + extra_chargesRepository.count() + " extra charges.");
		System.out.println("Loaded " + clientRepository.count() + " clients.");
		
	}
}
