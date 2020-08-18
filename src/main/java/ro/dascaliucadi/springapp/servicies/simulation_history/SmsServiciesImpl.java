package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;
import ro.dascaliucadi.springapp.simulation_history.SmsRepository;

@Service
public class SmsServiciesImpl implements SmsServicies {

	private final SmsRepository smsRepository;

	public SmsServiciesImpl(SmsRepository smsRepository) {
		this.smsRepository = smsRepository;
	}

	@Override
	public void addSms(Clients client, String receiveSms, String dateMessageSent, int nrOfSms, String smsType) {

		SmsHistory smsHistory = new SmsHistory();

		smsHistory.setClientId(client.getID());
		smsHistory.setDateSmsSent(dateMessageSent);
		smsHistory.setSentSms(client.getPhone());
		smsHistory.setReceiveSms(receiveSms);
		smsHistory.setNrOfSms(nrOfSms);
		smsHistory.setSmsType(smsType);

		smsRepository.save(smsHistory);
	}

	@Override
	public List<SmsHistory> getAllSms() {

		return smsRepository.findAll();
	}

	@Override
	public SmsHistory getSmsByClientId(int id) {
		List<SmsHistory> sms = new ArrayList<SmsHistory>();
		for (SmsHistory s : smsRepository.findAll()) {
			if (id == s.getClientId()) {
				sms.add(s);
			}
		}

		if (sms.size() == 0) {
			return null;
		}

		return sms.get(sms.size() - 1);
	}

	@Override
	public List<SmsHistory> getAllSmsById(int id) {
		List<SmsHistory> sms = new ArrayList<SmsHistory>();
		for (SmsHistory s : smsRepository.findAll()) {
			if (id == s.getClientId()) {
				sms.add(s);
			}
		}

		return sms;
	}
}
