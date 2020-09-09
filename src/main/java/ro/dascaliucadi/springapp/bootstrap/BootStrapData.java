package ro.dascaliucadi.springapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.clients.ClientsRepository;
import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.simulation_history.CDR;
import ro.dascaliucadi.springapp.simulation_history.CDRRepository;
import ro.dascaliucadi.springapp.subscription.Subscriptions;
import ro.dascaliucadi.springapp.subscription.SubscriptionsRepository;

@Component
public class BootStrapData implements CommandLineRunner {

	private Subscriptions s1, s2;
	private Extra_Charges e1, e2, e3;
	private Clients c1, c2, c3;
	private CDR cdr;
	
	private final ClientsRepository clientRepository;
	private final Extra_ChargesRepository extra_chargesRepository;
	private final SubscriptionsRepository subscriptionsRepository;
	private final CDRRepository cdrRepository;
	
	public BootStrapData(ClientsRepository clientRepository, Extra_ChargesRepository extra_chargesRepository, SubscriptionsRepository subscriptionsRepository, CDRRepository cdrRepository) {
		this.clientRepository = clientRepository;
		this.extra_chargesRepository = extra_chargesRepository;
		this.subscriptionsRepository = subscriptionsRepository;
		this.cdrRepository = cdrRepository;
		
	}
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Loading Clients data...");
		
		s1 = new Subscriptions(SubscriptionsEnum.Standard);
		s1.setID(1);
		subscriptionsRepository.save(s1);
		
		s2 = new Subscriptions(SubscriptionsEnum.Premium);
		s2.setID(2);
		subscriptionsRepository.save(s2);
		
		e1 = new Extra_Charges();
		e1.setExtra_ChargesType(1);
		e1.setID(1);
		e1.setClientId(1);
		extra_chargesRepository.save(e1);
		
		e2 = new Extra_Charges();
		e2.setExtra_ChargesType(1);
		e2.setID(2);
		e2.setClientId(2);
		extra_chargesRepository.save(e2);
		
		e3 = new Extra_Charges();
		e3.setExtra_ChargesType(1);
		e3.setID(3);
		e3.setClientId(3);
		extra_chargesRepository.save(e3);
		
		c1 = new Clients("Dascaliuc Adi", "Suceava", 800.78, "0743887339");
		c1.setSubscriptionType(s1.getSubscriptionType());
		c1.setExtraChargesType(e1.getExtra_ChargesType());
		clientRepository.save(c1);
		
		c2 = new Clients("Vasilescu Vasile", "Cluj", 777.77, "0213698574");
		c2.setSubscriptionType(s2.getSubscriptionType());
		c2.setExtraChargesType(e2.getExtra_ChargesType());
		clientRepository.save(c2);
		
		c3 = new Clients("Stefanescu Stefan", "Brasov", 34.56, "0216983574");
		c3.setSubscriptionType(s1.getSubscriptionType());
		c3.setExtraChargesType(e3.getExtra_ChargesType());
		clientRepository.save(c3);
		
		cdr = new CDR();
		cdrRepository.save(cdr);
		
		System.out.println("Loaded " + subscriptionsRepository.count() + " subscriptions.");
		System.out.println("Loaded " + extra_chargesRepository.count() + " extra charges.");
		System.out.println("Loaded " + clientRepository.count() + " clients.");
		System.out.println("Loaded " + cdrRepository.count() + " cdr's.");
		
	}
}
