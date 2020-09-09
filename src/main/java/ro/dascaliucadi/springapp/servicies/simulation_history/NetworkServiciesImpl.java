package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.MonthInvoiceEnum;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;
import ro.dascaliucadi.springapp.simulation_history.NetworkRepository;

@Service
@Configurable
public class NetworkServiciesImpl implements NetworkServicies {

	@Autowired
	private NetworkRepository networkRepository;

	private Date firstDate = null;
	private Date secondDate = null;

	@Override
	public void addNetwork(Clients client, String trafficStart, String trafficEnd, long minutesSpend,
			String trafficType) {
		NetworkHistory net = new NetworkHistory();

		net.setClientId(client.getID());
		net.setPhoneNumber(client.getPhone());
		net.setTrafficStart(trafficStart);
		net.setTrafficEnd(trafficEnd);
		net.setMBSpend(minutesSpend);
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

	@Override
	public List<NetworkHistory> getNetworkByClientIdAndCurrentDate(int clientId) {
		List<NetworkHistory> networks = new ArrayList<NetworkHistory>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		try {
			firstDate = formatter
					.parse(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			secondDate = formatter
					.parse(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		firstDate.setDate(1);
		secondDate.setDate(1);
		secondDate.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
		
		for (NetworkHistory net : networkRepository.findAll()) {
			try {
				if (net.getClientId() == clientId
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(net.getTrafficStart()).after(firstDate)
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(net.getTrafficEnd()).before(secondDate)) {
					networks.add(net);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return networks;
	}

	@Override
	public long getTotalMbByClient(Clients client) {
		long totalMb = 0;
		for (NetworkHistory net : networkRepository.findAll()) {
			if (net.getClientId() == client.getID()) {
				totalMb += net.getMBSpend();
			}
		}
		return totalMb;
	}
}
