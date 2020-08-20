package ro.dascaliucadi.springapp.simulation_history;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calls_history")
public class CallsHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int ID;

	@Column(name = "client_id")
	private int ClientId;

	@Column(name = "make_call")
	String MakeCall;

	@Column(name = "receive_call")
	String ReceiveCall;

	@Column(name = "start_call")
	private String startCall;

	@Column(name = "end_call")
	private String endCall;

	@Column(name = "call_minutes")
	public long callMinutes;

	@Column(name = "call_type")
	private String CallType;

	public CallsHistory() {
	}

	public int getID() {
		return ID;
	}

	public void setID(int clientID) {
		this.ID = clientID;
	}

	public String getMakeCall() {
		return MakeCall;
	}

	public void setMakeCall(String makeCall) {
		MakeCall = makeCall;
	}

	public String getReceiveCall() {
		return ReceiveCall;
	}

	public void setReceiveCall(String receiveCall) {
		ReceiveCall = receiveCall;
	}

	public String getStartCall() {
		return startCall;
	}

	public void setStartCall(String startCall) {
		this.startCall = startCall;
	}

	public void setStartCall() {
		
		this.startCall = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

	}

	public String getEndCall() {
		return this.endCall;
	}

	public void setEndCall(String endCall) {
		this.endCall = endCall;
	}

	public void setEndCall() {
		this.endCall = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

	}

	public long getCallMinutes() {
		return callMinutes;
	}

	public void setCallMinutes(long callMinutes) {
		this.callMinutes = callMinutes;
	}

	public String getCallType() {
		return CallType;
	}

	public void setCallType(String callType) {
		CallType = callType;
	}

	public int getClientId() {
		return ClientId;
	}

	public void setClientId(int clientId) {
		ClientId = clientId;
	}

}
