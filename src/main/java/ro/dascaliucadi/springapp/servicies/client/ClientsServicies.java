package ro.dascaliucadi.springapp.servicies.client;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;

@Service
public interface ClientsServicies {

	Clients findClientByID(int id);
	Clients findClientByPhone(String phoneNumber);
	Clients saveClient(Clients client);
	Clients addClient(Clients newClient);
	Clients getByPhoneNumber(String phone);
	
	List<Clients> findAllClients();
	List<Clients> getAllClients();
	
	void deleteClient(Clients delClient);
	void updateClient(Clients client);
	void updateClientBalance(Clients client, double balance);
}
