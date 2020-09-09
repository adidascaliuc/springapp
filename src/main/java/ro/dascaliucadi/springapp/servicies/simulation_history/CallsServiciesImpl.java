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
import ro.dascaliucadi.springapp.simulation_history.CallsRepository;

@Service
@Configurable
public class CallsServiciesImpl implements CallsServicies {
	private Date firstDate = null;
	private Date secondDate = null;

	@Autowired
	private CallsRepository callsRepository;

	@Override
	public void addCall(Clients client, String receiveCall, String startCall, String endCall, long callMinutes,
			String callType) {
		CallsHistory calls = new CallsHistory();
		calls.setCallMinutes(callMinutes);
		calls.setClientId(client.getID());
		calls.setMakeCall(client.getPhone());
		calls.setReceiveCall(receiveCall);
		calls.setEndCall(endCall);
		calls.setStartCall(startCall);
		calls.setCallType(callType);

		callsRepository.save(calls);

	}

	@Override
	public List<CallsHistory> getAllCalls() {

		return callsRepository.findAll();
	}

	@Override
	public void updateCallType(CallsHistory call, String callType) {
		call.setCallType(callType);
		callsRepository.save(call);
	}

	@Override
	public CallsHistory getCallByClientId(int id) {
		List<CallsHistory> calls = new ArrayList<CallsHistory>();
		for (CallsHistory c : callsRepository.findAll()) {
			if (id == c.getClientId()) {
				calls.add(c);
			}
		}
		if (calls.size() == 0) {
			return null;
		}
		return calls.get(calls.size() - 1);
	}

	@Override
	public List<CallsHistory> getAllCallsByClientId(int id) {
		List<CallsHistory> calls = new ArrayList<CallsHistory>();
		for (CallsHistory c : callsRepository.findAll()) {
			if (id == c.getClientId()) {
				calls.add(c);
			}
		}
		return calls;
	}

	@Override
	public List<CallsHistory> getCallsByClientIdAndCurrentDate(int clientId) {
		List<CallsHistory> calls = new ArrayList<CallsHistory>();
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
		
		for (CallsHistory call : callsRepository.findAll()) {
			try {
				if (call.getClientId() == clientId
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(call.getStartCall()).after(firstDate)
						&& new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(call.getEndCall()).before(secondDate)) {
					calls.add(call);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return calls;
	}

	@Override
	public long getTotalMinuteByClientIdAndCallType(Clients client, String callType) {
		long totalMinutes = 0;
		for (CallsHistory call : callsRepository.findAll()) {
			if (call.getClientId() == client.getID() && call.getCallType().equals(callType)) {
				totalMinutes += call.getCallMinutes();
			}
		}
		return totalMinutes;
	}
}
