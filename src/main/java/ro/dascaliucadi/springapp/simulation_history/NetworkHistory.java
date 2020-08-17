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
@Table(name = "network_history")
public class NetworkHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int ID;

	@Column(name = "client_id")
	public int ClientId;

	@Column(name = "phone_number")
	public String PhoneNumber;

	@Column(name = "traffic_start")
	public String TrafficStart;

	@Column(name = "traffic_end")
	public String TrafficEnd;

	@Column(name = "minutes_spend")
	public long MinutesSpend;

	@Column(name = "traffic_type")
	public String TrafficType;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getClientId() {
		return ClientId;
	}

	public void setClientId(int clientId) {
		ClientId = clientId;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getTrafficStart() {
		return TrafficStart;
	}

	public void setTrafficStart(String trafficStart) {
		TrafficStart = trafficStart;
	}
	
	public void setTrafficStart() {
		TrafficStart = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public String getTrafficEnd() {
		return TrafficEnd;
	}

	public void setTrafficEnd(String trafficEnd) {
		TrafficEnd = trafficEnd;
	}
	
	public void setTrafficEnd() {
		TrafficEnd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public long getMinutesSpend() {
		return MinutesSpend;
	}

	public void setMinutesSpend(long minutesSpend) {
		MinutesSpend = minutesSpend;
	}

	public String getTrafficType() {
		return TrafficType;
	}

	public void setTrafficType(String trafficType) {
		TrafficType = trafficType;
	}

}
