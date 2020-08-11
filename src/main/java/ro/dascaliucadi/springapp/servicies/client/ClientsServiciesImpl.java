package ro.dascaliucadi.springapp.servicies.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.clients.ClientsRepository;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.subscription.SubscriptionsRepository;

@Service
public class ClientsServiciesImpl implements ClientsServicies {

	private final ClientsRepository clientRepository;
	private final SubscriptionsRepository subscriptionRepository;
	private final Extra_ChargesRepository extra_chargesRepository;

	public ClientsServiciesImpl(ClientsRepository clientRepository, SubscriptionsRepository subscriptionRepository,
			Extra_ChargesRepository extra_chargesRepository) {
		this.clientRepository = clientRepository;
		this.subscriptionRepository = subscriptionRepository;
		this.extra_chargesRepository = extra_chargesRepository;

	}

	@Override
	public Clients findClientByID(int ID) {
		return clientRepository.findById(ID).get();
	}

	@Override
	public List<Clients> findAllClients() {
		return clientRepository.findAll();
	}

	@Override
	public Clients saveClient(Clients client) {
		return clientRepository.save(client);

	}

	@Override
	public Clients addClient(Clients newClient) {
		newClient.setSubscriptionType(1);
		newClient.setExtra_ChargesType(1);
		clientRepository.save(newClient);
		return newClient;
	}

	@Override
	public void deleteClient(Clients delClient) {
		clientRepository.delete(delClient);
	}

	@Override
	public List<Clients> getAllClients() {
		List<Clients> clients = new ArrayList<Clients>();
		for (Clients client : clientRepository.findAll()) {
			clients.add(client);
		}
		return clients;
	}

	@Override
	public void updateClient(int id, String name, String address, String phone, double balance, String subType, String extraType) {
		Clients c = new Clients();
		c.setID(id);
		c.setName(name);
		c.setAddress(address);
		c.setBalance(balance);
		c.setPhone(phone);
		clientRepository.save(c);
	}
}
