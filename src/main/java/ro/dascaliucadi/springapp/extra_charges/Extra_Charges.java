package ro.dascaliucadi.springapp.extra_charges;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import ro.dascaliucadi.springapp.client.Client;
import ro.dascaliucadi.springapp.enumerari.Extra_ChargesEnum;

@Entity
@Table(name = "extra_charges")
public class Extra_Charges {
	
	@Transient
	private final String PREMIUM = "Premium";
	@Transient
	private final String STANDARD = "Standard";
	

	@Id
	@NotNull
	@Column(name = "id")
	private int ID;

	@NotNull
	@Column(name = "client_name")
	private String ClientName;
	
	@NotNull
	@Column(name="extra_charges_type")
	private String Extra_ChargesType;

	@NotNull
	@Column(name = "network_call")
	private double NetworkCall;

	@NotNull
	@Column(name = "network_sms")
	private double NetworkSMS;

	@NotNull
	@Column(name = "call")
	private double Call;

	@NotNull
	@Column(name = "sms")
	private double SMS;

	@NotNull
	@Column(name = "internet_traffic")
	private double InternetTraffic;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id", nullable = false, updatable = false, insertable = false)
	private Client client;

	public Extra_Charges() {
	};

	public Extra_Charges(Extra_ChargesEnum extra_chargesType, Client client) {
		if (extra_chargesType.equals(Extra_ChargesEnum.premium)) {
			this.setCall(2000);
			this.setInternetTraffic(1000);
			this.setClientName(client.getName());
			this.setNetworkCall(500);
			this.setNetworkSMS(250);
			this.setSMS(1000);
			this.setID(client.getID());
			this.setExtra_ChargesType(PREMIUM);
			
		} else {
			this.setCall(500);
			this.setInternetTraffic(250);
			this.setClientName(client.getName());
			this.setNetworkCall(75);
			this.setNetworkSMS(50);
			this.setSMS(250);
			this.setID(client.getID());
			this.setExtra_ChargesType(STANDARD);
		}
	}

	public int getID() {
		return ID;
	}

	public void setID(int Id) {
		ID = Id;
	}

	

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	public String getExtra_ChargesType() {
		return Extra_ChargesType;
	}

	public void setExtra_ChargesType(String extra_ChargesType) {
		Extra_ChargesType = extra_ChargesType;
	}

	public double getNetworkCall() {
		return NetworkCall;
	}

	public void setNetworkCall(double networkCall) {
		NetworkCall = networkCall;
	}

	public double getNetworkSMS() {
		return NetworkSMS;
	}

	public void setNetworkSMS(double networkSMS) {
		NetworkSMS = networkSMS;
	}

	public double getCall() {
		return Call;
	}

	public void setCall(double call) {
		Call = call;
	}

	public double getSMS() {
		return SMS;
	}

	public void setSMS(double sMS) {
		SMS = sMS;
	}

	public double getInternetTraffic() {
		return InternetTraffic;
	}

	public void setInternetTraffic(double internetTraffic) {
		InternetTraffic = internetTraffic;
	}

}
