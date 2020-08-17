package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;

public interface NetworkServicies {
	
	void addNetwork(Clients client, String trafficStart, String trafficEnd, long minutesSpend, String trafficType);
	NetworkHistory getNetworkByClientId(int id);
	
	List<NetworkHistory> getAllNetworkById(int id);
	List<NetworkHistory> getAllTraffic();
}
