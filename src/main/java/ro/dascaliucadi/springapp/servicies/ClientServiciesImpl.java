package ro.dascaliucadi.springapp.servicies;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.client.ClientRepository;

@Service
public class ClientServiciesImpl implements ClientServicies {

	private final ClientRepository clientRepository;
	
	public ClientServiciesImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
		
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
		return newClient;
	}
	
	@Override
	public void deleteClient(Client delClient) {
		clientRepository.delete(delClient);
	}
}
