package ro.dascaliucadi.springapp.servicies.client;

import java.util.List;

import ro.dascaliucadi.springapp.client.Client;

public interface ClientServicies {

	Client findClientByID(int id);
	List<Client> findAllClients();
	Client saveClient(Client client);
	Client addClient(Client newClient);
	void deleteClient(Client delClient);
	List<Client> getAllClients();
}
