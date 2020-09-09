package ro.dascaliucadi.springapp.servicies.extra_charges;

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
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;
import ro.dascaliucadi.springapp.simulation_history.CDR;

@Service
@Configurable
public class Extra_ChargesServiciesIml implements Extra_ChargesServicies {

	@Autowired
	private Extra_ChargesRepository extra_chargesRepository;

	@Autowired
	private CDR cdr;

	@Override
	public List<Extra_Charges> getAllExtra_Charges() {
		List<Extra_Charges> extra_chargeses = new ArrayList<Extra_Charges>();
		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			extra_chargeses.add(extra);
		}
		return extra_chargeses;
	}

	@Override
	public List<Extra_Charges> getAllExtra_ChargesByClientIdForPreviousMonth(int id) {
		List<Extra_Charges> extra_charges = new ArrayList<Extra_Charges>();

		Date firstDate = null;
		Date secondDate = null;

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
		firstDate.setMonth(Calendar.getInstance().get(Calendar.MONTH) - 1);

		secondDate.setDate(1);
		secondDate.setMonth(Calendar.getInstance().get(Calendar.MONTH));

		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			try {
				if (extra.getClientId() == id
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(extra.getMonthGeneratedExtra())
								.after(firstDate)
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(extra.getMonthGeneratedExtra())
								.before(secondDate)) {
					extra_charges.add(extra);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return extra_charges;
	}

	@Override
	public List<Extra_Charges> getAllExtra_ChargesByClientId(int id) {
		List<Extra_Charges> extra_charges = new ArrayList<Extra_Charges>();

		Date firstDate = null;
		Date secondDate = null;

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

		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			try {
				if (extra.getClientId() == id
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(extra.getMonthGeneratedExtra())
								.after(firstDate)
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(extra.getMonthGeneratedExtra())
								.before(secondDate)) {
					extra_charges.add(extra);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return extra_charges;
	}

	@Override
	public void addExtra_Charges(Clients client, double call, double sms, double networkCall, double networkSms,
			double internetTraffic) {

		Extra_Charges extra = new Extra_Charges();
		extra.setMonthGeneratedExtra();
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
	public void updateExtra_Charges(int extra_chargesID, Clients client, double call, double sms, double networkCall,
			double networkSms, double internetTraffic) {
		Extra_Charges extra = new Extra_Charges();

		extra.setID(extra_chargesID);
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
		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			if (extra.getClientId() == client.getID()) {
				total = extra.getCall() + extra.getSMS() + extra.getNetworkCall() + extra.getNetworkSMS()
						+ extra.getInternetTraffic();
			}
		}
		return total;
	}

	@Override
	public Extra_Charges getExtraChargesForClientByClientId(int clientID) {
		for (Extra_Charges extra : extra_chargesRepository.findAll()) {
			if (extra.getClientId() == clientID) {
				return extra;
			}
		}

		Extra_Charges newExtra = new Extra_Charges();
		newExtra.setClientId(clientID);
		return newExtra;
	}

}
