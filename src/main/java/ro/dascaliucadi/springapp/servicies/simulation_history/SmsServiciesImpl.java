package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	
	@SuppressWarnings("deprecation")
	@Override
	public List<SmsHistory> getSmsByClientIdAndCurrentDate(int clientId) {
		List<SmsHistory> smss = new ArrayList<SmsHistory>();
		SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		Date firstDate = null;
		try {
			firstDate = formatter.parse( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()) );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date secondDate = null;
		try {
			secondDate = formatter.parse( new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()) );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		firstDate.setDate(1);
		System.out.println("Here is firstDate: " + firstDate);
		secondDate.setDate(1);
		secondDate.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
		System.out.println("Here is secondDate: " + secondDate);
		
		for(SmsHistory sms : smsRepository.findAll()) {
			try {
				if(sms.getClientId() == clientId &&
						new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(sms.getDateSmsSent()).after(firstDate)
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(sms.getDateSmsSent()).before(secondDate)) {
					smss.add(sms);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return smss;
	}
	
	@Override
	public int getTotalSentSmsByCliendIdAndSmsType(Clients client, String smsType) {
		int totalSms = 0;
		for(SmsHistory sms : smsRepository.findAll()) {
			if(sms.getClientId() == client.getID() && sms.getSmsType().equals(smsType)) {
				totalSms += sms.getNrOfSms();
			}
		}
		return totalSms;
	}
}
