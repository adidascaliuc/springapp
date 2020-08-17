package ro.dascaliucadi.springapp.servicies.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.clients.ClientsRepository;

@Service
public class ClientsServiciesImpl implements ClientsServicies {

	private final ClientsRepository clientRepository;

	public ClientsServiciesImpl(ClientsRepository clientRepository) {
		this.clientRepository = clientRepository;

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
	public void updateClient(Clients client) {
	
		clientRepository.save(client);
	}

	@Override
	public void updateClientBalance(Clients client, double balance) {
		client.setBalance(balance);
		clientRepository.save(client);
	}

	@Override
	public Clients findClientByPhone(String phoneNumber) {
		for (Clients c : clientRepository.findAll()) {
			if (c.getPhone().equals(phoneNumber)) {
				return c;
			}
		}
		return null;
	}

}
