package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;

public interface SmsServicies {

	void addSms(Clients client, String receiveSms, String dateMessageSent, int nrOfSms, String smsType);

	int getTotalSentSmsByCliendIdAndSmsType(Clients client, String smsType);
	
	SmsHistory getSmsByClientId(int id);
	
	List<SmsHistory> getSmsByClientIdAndCurrentDate(int clientId);
	List<SmsHistory> getAllSmsById(int id);
	List<SmsHistory> getAllSms();
}
