package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
@Service
public interface CallsServicies {
	void addCall(Clients client, String receiveCall, String startCall, String endCall, long callMinutes, String callType);
	void updateCallType(CallsHistory call, String callType);
	
	long getTotalMinuteByClientIdAndCallType(Clients client, String callType);
	
	CallsHistory getCallByClientId(int id);
	
	List<CallsHistory> getAllCalls();
	List<CallsHistory> getAllCallsByClientId(int id);
	List<CallsHistory> getCallsByClientIdAndCurrentDate(int clientId);
	
}
