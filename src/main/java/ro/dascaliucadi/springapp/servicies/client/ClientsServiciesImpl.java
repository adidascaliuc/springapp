package ro.dascaliucadi.springapp.servicies.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.clients.ClientsRepository;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;

@Service
@Configurable
public class ClientsServiciesImpl implements ClientsServicies {
	
	@Autowired
	private  ClientsRepository clientRepository;
	
	@Autowired
	private  Extra_ChargesRepository extra_chargesRepository;
	

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
		Extra_Charges e;
		newClient.setSubscriptionType(newClient.getSubscriptionType());
		
		e = new Extra_Charges();
		e.setExtra_ChargesType(newClient.getExtraChargesType());
		e.setClientId(newClient.getID());
		
		newClient.setExtraChargesType(1);
		
		clientRepository.save(newClient);
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
