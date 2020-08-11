package ro.dascaliucadi.springapp.subscription;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;

@Entity
@Table(name="subscriptions")
public class Subscriptions {
	
	@Transient
	private final String PREMIUM = "Premium";
	@Transient
	private final String STANDARD = "Standard";
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID;
	
	@NotNull
	@Column(name="monthly_cost")
	private double MonthlyCost;
	
	@NotNull
	@Column(name="network_minutes_included")
	private double NetworkMinutesIncluded;
	
	@NotNull
	@Column(name="network_sms_included")
	private double NetworkSMSIncluded;
	
	@NotNull
	@Column(name="minutes_included")
	private double MinutesIncluded;
	
	@NotNull
	@Column(name="sms_included")
	private double SMSIncluded;
	
	@NotNull
	@Column(name="traffic_included")
	private double TrafficIncluded;
	
	@NotNull
	@Column(name="subscription_type")
	private int SubscriptionType;
	
	
	public Subscriptions () { }
	
	public Subscriptions(SubscriptionsEnum subscriptionType) {
		
		if(subscriptionType.equals(SubscriptionsEnum.premium)) {
			this.setMinutesIncluded(1000);
			this.setNetworkMinutesIncluded(500);
			this.setNetworkSMSIncluded(300);
			this.setSMSIncluded(1500);
			this.setTrafficIncluded(60000);
			this.setMonthlyCost(12);
			this.setSubscriptionType(1);
		} else {
			this.setMinutesIncluded(300);
			this.setNetworkMinutesIncluded(120);
			this.setNetworkSMSIncluded(50);
			this.setSMSIncluded(500);
			this.setTrafficIncluded(3000);
			this.setMonthlyCost(300);
			this.setMonthlyCost(6);
			this.setSubscriptionType(2);
		}
	}
	
	
	
	public int getID() {
		return ID;
	}

	public void setID(int Id) {
		ID = Id;
	}

	public int getSubscriptionType() {
		return SubscriptionType;
	}

	public void setSubscriptionType(int subscriptionType) {
		SubscriptionType = subscriptionType;
	}

	public double getMonthlyCost() {
		return MonthlyCost;
	}

	public void setMonthlyCost(double monthlyCost) {
		MonthlyCost = monthlyCost;
	}

	public double getNetworkMinutesIncluded() {
		return NetworkMinutesIncluded;
	}

	public void setNetworkMinutesIncluded(double networkMinutesIncluded) {
		NetworkMinutesIncluded = networkMinutesIncluded;
	}

	public double getNetworkSMSIncluded() {
		return NetworkSMSIncluded;
	}

	public void setNetworkSMSIncluded(double networkSMSIncluded) {
		NetworkSMSIncluded = networkSMSIncluded;
	}

	public double getMinutesIncluded() {
		return MinutesIncluded;
	}

	public void setMinutesIncluded(double minutesIncluded) {
		MinutesIncluded = minutesIncluded;
	}

	public double getSMSIncluded() {
		return SMSIncluded;
	}

	public void setSMSIncluded(double sMSIncluded) {
		SMSIncluded = sMSIncluded;
	}

	public double getTrafficIncluded() {
		return TrafficIncluded;
	}

	public void setTrafficIncluded(double trafficIncluded) {
		TrafficIncluded = trafficIncluded;
	}
	
}
