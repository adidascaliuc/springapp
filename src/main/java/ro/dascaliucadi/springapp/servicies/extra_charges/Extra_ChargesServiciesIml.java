package ro.dascaliucadi.springapp.servicies.extra_charges;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.simulation_history.CDR;

@Service
@ComponentScan
public class Extra_ChargesServiciesIml implements Extra_ChargesServicies {

	private final Extra_ChargesRepository extra_chargesRepository;
	private final CDR cdr;

	public Extra_ChargesServiciesIml(Extra_ChargesRepository extra_chargesRepository, CDR cdr) {
		this.extra_chargesRepository = extra_chargesRepository;
		this.cdr = cdr;
	}

	@Override
	public List<Extra_Charges> getAllExtra_Charges() {
		List<Extra_Charges> extra_chargeses = new ArrayList<Extra_Charges>();
		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			extra_chargeses.add(extra);
		}
		return extra_chargeses;
	}

	@Override
	public List<Extra_Charges> getAllExtra_ChargesByClientId(int id) {
		List<Extra_Charges> extra_charges = new ArrayList<Extra_Charges>();
		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			if (extra.getClientId() == id) {
				extra_charges.add(extra);
			}
		}
		return extra_charges;
	}

	@Override
	public void addExtra_Charges(Clients client, double call, double sms, double networkCall, double networkSms,
			double internetTraffic) {

		Extra_Charges extra = new Extra_Charges();
		extra.setCall(call * cdr.getPerCallMinute());
		extra.setClientId(client.getID());
		extra.setExtra_ChargesType(client.getExtraChargesType());
		extra.setInternetTraffic(internetTraffic * cdr.getPerMbInternetTraffic());
		extra.setNetworkCall(networkCall * cdr.getPerNetworkCallMinute());
		extra.setNetworkSMS(networkSms * cdr.getPerNetworkSms());
		extra.setSMS(sms * cdr.getPerSms());

		extra_chargesRepository.save(extra);
	}

	@Override
	public void updateExtra_Charges(Clients client, double call, double sms, double networkCall, double networkSms,
			double internetTraffic) {
		Extra_Charges extra = new Extra_Charges();
		
		extra.setID(client.getExtra_charges().getID());
		extra.setCall(call * cdr.getPerCallMinute());
		extra.setClientId(client.getID());
		extra.setExtra_ChargesType(client.getExtraChargesType());
		extra.setInternetTraffic(internetTraffic * cdr.getPerMbInternetTraffic());
		extra.setNetworkCall(networkCall * cdr.getPerNetworkCallMinute());
		extra.setNetworkSMS(networkSms * cdr.getPerNetworkSms());
		extra.setSMS(sms * cdr.getPerSms());

		extra_chargesRepository.save(extra);

	}
	
	@Override
	public double getTotalToPayByClientId(Clients client) {
		double total = 0;
		for(Extra_Charges extra : extra_chargesRepository.findAll()) {
			if(extra.getClientId() == client.getID()) {
				total = extra.getCall() + extra.getSMS() + extra.getNetworkCall() 
				+ extra.getNetworkSMS() + extra.getInternetTraffic();
			}
		}
		return total;
	}

}
