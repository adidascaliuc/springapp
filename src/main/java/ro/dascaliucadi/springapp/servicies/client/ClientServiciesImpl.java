package ro.dascaliucadi.springapp.servicies.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.client.ClientRepository;
import ro.dascaliucadi.springapp.enumerari.Extra_ChargesEnum;
import ro.dascaliucadi.springapp.enumerari.SubscriptionEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.subscription.Subscription;
import ro.dascaliucadi.springapp.subscription.SubscriptionRepository;

@Service
public class ClientServiciesImpl implements ClientServicies {

	private final ClientRepository clientRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final Extra_ChargesRepository extra_chargesRepository;
	
	public ClientServiciesImpl(ClientRepository clientRepository, SubscriptionRepository subscriptionRepository, Extra_ChargesRepository extra_chargesRepository) {
		this.clientRepository = clientRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.extra_chargesRepository = extra_chargesRepository;
		
	}
	@Override
	public Client findClientByID(int ID) {
		return clientRepository.findById(ID).get();
	}

	@Override
	public List<Client> findAllClients() {
		return clientRepository.findAll();
	}
	
	@Override
	public Client saveClient(Client client) {
		return clientRepository.save(client);
		
	}
	
	@Override
	public Client addClient(Client newClient) {
		clientRepository.save(newClient);
		subscriptionRepository.save(new Subscription(SubscriptionEnum.standard, newClient));
		extra_chargesRepository.save(new Extra_Charges(Extra_ChargesEnum.standard, newClient));
		return newClient;
	}
	
	@Override
	public void deleteClient(Client delClient) {
		subscriptionRepository.deleteById(delClient.getID());
		extra_chargesRepository.deleteById(delClient.getID());
		clientRepository.delete(delClient);
	}
	
	@Override
	public List<Client> getAllClients() {
		List<Client> clients = new ArrayList<Client>();
		for(Client client : clientRepository.findAll()) {
			clients.add(client);
		}
		return clients;
	}
}
