package ro.dascaliucadi.springapp.extra_charges;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import ro.dascaliucadi.springapp.enumerari.Extra_ChargesEnum;

@Entity
@Table(name = "extra_charges")
public class Extra_Charges {
	
	@Transient
	private final String PREMIUM = "Premium";
	@Transient
	private final String STANDARD = "Standard";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID;
	
	@NotNull
	@Column(name="extra_charges_type")
	private int Extra_ChargesType;

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

	public Extra_Charges() {
			this.setCall(0);
			this.setInternetTraffic(0);
			this.setNetworkCall(0);
			this.setNetworkSMS(0);
			this.setSMS(0);
			this.setExtra_ChargesType(1);
	}

	public int getID() {
		return ID;
	}

	public void setID(int Id) {
		ID = Id;
	}

	public int getExtra_ChargesType() {
		return Extra_ChargesType;
	}

	public void setExtra_ChargesType(int extra_ChargesType) {
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
