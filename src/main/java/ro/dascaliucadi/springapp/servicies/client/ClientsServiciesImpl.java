package ro.dascaliucadi.springapp.servicies.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.clients.ClientsRepository;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;

@Service
public class ClientsServiciesImpl implements ClientsServicies {

	private final ClientsRepository clientRepository;
	private final Extra_ChargesRepository extra_chargesRepository;
	private Extra_Charges e;

	public ClientsServiciesImpl(ClientsRepository clientRepository, Extra_ChargesRepository extra_chargesRepository) {
		this.clientRepository = clientRepository;
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
		e = null;
		
		newClient.setSubscriptionType(1);
		clientRepository.save(newClient);
		
		e = new Extra_Charges();
		e.setExtra_ChargesType(1);
		e.setClientId(newClient.getID());
		extra_chargesRepository.save(e);
		
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
	
	@Override
	public Clients getByPhoneNumber(String phone) {
		for(Clients client : clientRepository.findAll()) {
			if(client.getPhone().equals(phone)) {
				return client;
			}
		}
		return null;
	}

}
