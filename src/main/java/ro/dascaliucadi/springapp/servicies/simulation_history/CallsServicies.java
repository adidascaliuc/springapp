package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import org.joda.time.DateTime;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;

public interface CallsServicies {
	void addCall(Clients client, String receiveCall, String startCall, String endCall, long callMinutes, String callType);
	List<CallsHistory> getAllCalls();
	CallsHistory getCallByClientId(int id);
	List<CallsHistory> getAllCallsByClientId(int id);
	void updateCallType(CallsHistory call, String callType);
}
