package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;
import ro.dascaliucadi.springapp.simulation_history.NetworkRepository;

@Service
public class NetworkServiciesImpl implements NetworkServicies {

	private final NetworkRepository networkRepository;

	public NetworkServiciesImpl(NetworkRepository networkRepository) {
		this.networkRepository = networkRepository;

	}

	@Override
	public void addNetwork(Clients client, String trafficStart, String trafficEnd, long minutesSpend,
			String trafficType) {
		NetworkHistory net = new NetworkHistory();

		net.setClientId(client.getID());
		net.setPhoneNumber(client.getPhone());
		net.setTrafficStart(trafficStart);
		net.setTrafficEnd(trafficEnd);
		net.setMinutesSpend(minutesSpend);
		net.setTrafficType(trafficType);

		networkRepository.save(net);
	}

	@Override
	public List<NetworkHistory> getAllTraffic() {
		return networkRepository.findAll();
	}

	@Override
	public NetworkHistory getNetworkByClientId(int id) {
		List<NetworkHistory> net = new ArrayList<NetworkHistory>();
		for (NetworkHistory n : networkRepository.findAll()) {
			if (id == n.getClientId()) {
				net.add(n);
			}
		}

		if (net.size() == 0) {
			return null;
		}

		return net.get(net.size() - 1);
	}

	@Override
	public List<NetworkHistory> getAllNetworkById(int id) {
		List<NetworkHistory> net = new ArrayList<NetworkHistory>();
		for (NetworkHistory n : networkRepository.findAll()) {
			if (id == n.getClientId()) {
				net.add(n);
			}
		}

		return net;
	}
}
