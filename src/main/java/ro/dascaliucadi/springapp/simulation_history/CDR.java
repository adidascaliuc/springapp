package ro.dascaliucadi.springapp.simulation_history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cdr")
public class CDR {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int ID;

	@Column(name = "per_call_minute")
	private double PerCallMinute;

	@Column(name = "per_sms")
	private double PerSms;

	@Column(name = "per_network_call_minute")
	private double PerNetworkCallMinute;

	@Column(name = "per_network_sms")
	private double PerNetworkSms;

	@Column(name = "per_mb_internet_traffic")
	private double PerMbInternetTraffic;

	public CDR() {
		this.setPerCallMinute(2);
		this.setPerSms(1);
		this.setPerNetworkCallMinute(4);
		this.setPerNetworkSms(3);
		this.setPerMbInternetTraffic(5);
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getPerCallMinute() {
		return PerCallMinute;
	}

	public void setPerCallMinute(double perCallMinute) {
		PerCallMinute = perCallMinute;
	}

	public double getPerSms() {
		return PerSms;
	}

	public void setPerSms(double perSms) {
		PerSms = perSms;
	}

	public double getPerNetworkCallMinute() {
		return PerNetworkCallMinute;
	}

	public void setPerNetworkCallMinute(double perNetworkCallMinute) {
		PerNetworkCallMinute = perNetworkCallMinute;
	}

	public double getPerNetworkSms() {
		return PerNetworkSms;
	}

	public void setPerNetworkSms(double perNetworkSms) {
		PerNetworkSms = perNetworkSms;
	}

	public double getPerMbInternetTraffic() {
		return PerMbInternetTraffic;
	}

	public void setPerMbInternetTraffic(double perMbInternetTraffic) {
		PerMbInternetTraffic = perMbInternetTraffic;
	}

}
