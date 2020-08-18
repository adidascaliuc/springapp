package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
import ro.dascaliucadi.springapp.simulation_history.CallsRepository;

@Service
public class CallsServiciesImpl implements CallsServicies {
	private final CallsRepository callsRepository;

	public CallsServiciesImpl(CallsRepository callsRepository) {
		this.callsRepository = callsRepository;
	}

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
}
