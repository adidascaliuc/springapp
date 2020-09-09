package ro.dascaliucadi.springapp.servicies.extra_charges;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;

@Service
public interface Extra_ChargesServicies {

	double getTotalToPayByClientId(Clients client);
	
	List<Extra_Charges> getAllExtra_Charges();
	List<Extra_Charges> getAllExtra_ChargesByClientId(int id);
	List<Extra_Charges> getAllExtra_ChargesByClientIdForPreviousMonth(int id);
	
	Extra_Charges getExtraChargesForClientByClientId(int clientID);
	
	void addExtra_Charges(Clients client, double call, double sms, double networkCall, double networkSms, double internetTraffic);
	void updateExtra_Charges(int extra_chargesID, Clients client, double call, double sms, double networkCall, double networkSms, double internetTraffic);
}
