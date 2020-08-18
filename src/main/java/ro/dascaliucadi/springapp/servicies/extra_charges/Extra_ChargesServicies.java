package ro.dascaliucadi.springapp.servicies.extra_charges;

import java.util.List;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;

public interface Extra_ChargesServicies {

	List<Extra_Charges> getAllExtra_Charges();
	List<Extra_Charges> getAllExtra_ChargesByClientId(int id);
	
	List<Extra_Charges> getAllExtra_ChargesByClientIdOrderByDate(int clientId, String startDate, String endDate);
	void addExtra_Charges(Clients client, double call, double sms, double networkCall, double networkSms, double internetTraffic);
}
