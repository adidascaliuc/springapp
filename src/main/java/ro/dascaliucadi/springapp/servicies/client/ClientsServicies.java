package ro.dascaliucadi.springapp.servicies.client;

import java.util.List;

import ro.dascaliucadi.springapp.clients.Clients;

public interface ClientsServicies {

	Clients findClientByID(int id);
	List<Clients> findAllClients();
	Clients saveClient(Clients client);
	Clients addClient(Clients newClient);
	void deleteClient(Clients delClient);
	List<Clients> getAllClients();
	void updateClient(int id, String name, String address, String phone, double balance, String subType, String extraType);
}
