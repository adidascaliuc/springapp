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
@Table(name = "sms_history")
public class SmsHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int ID;

	@Column(name = "client_id")
	private int ClientId;

	@Column(name = "receive_sms")
	String ReceiveSms;

	@Column(name = "sent_sms")
	String SentSms;

	@Column(name = "date_sms_sent")
	private String DateSmsSent;

	@Column(name = "sms_type")
	private String smsType;

	@Column(name = "nr_of_sms")
	private int NrOfSms;

	public SmsHistory() {
	}

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

	public String getDateSmsSent() {
		return DateSmsSent;
	}

	public void setDateSmsSent(String dateSmsSent) {
		DateSmsSent = dateSmsSent;
	}

	public void setDateSmsSent() {
		DateSmsSent = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public String getReceiveSms() {
		return ReceiveSms;
	}

	public void setReceiveSms(String receiveSms) {
		ReceiveSms = receiveSms;
	}

	public void setReceiveSms() {
		ReceiveSms = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
	}

	public String getSentSms() {
		return SentSms;
	}

	public void setSentSms(String sentSms) {
		SentSms = sentSms;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public int getNrOfSms() {
		return NrOfSms;
	}

	public void setNrOfSms(int nrOfSms) {
		NrOfSms = nrOfSms;
	}

}
