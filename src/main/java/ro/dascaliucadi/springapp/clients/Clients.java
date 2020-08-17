package ro.dascaliucadi.springapp.clients;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;
import ro.dascaliucadi.springapp.simulation_history.NetworkHistory;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;
import ro.dascaliucadi.springapp.subscription.Subscriptions;

@Entity
@Table(name = "CLIENTS")
public class Clients {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "id")
	private int ID;

	@NotNull
	@Column(name = "name")
	private String Name;

	@NotNull
	@Column(name = "address")
	private String Address;

	@NotNull
	@Column(name = "phone")
	private String Phone;

	@NotNull
	@Column(name = "balance")
	private double Balance;

	@NotNull
	@Column(name = "subscription_type")
	private int SubscriptionType;
	
	@NotNull
	@Column(name = "extra_charges_type")
	private int ExtraChargesType;

	@Transient
	private CallsHistory callHistory;

	@Transient
	private SmsHistory smsHistory;

	@Transient
	private NetworkHistory networkHistory = new NetworkHistory();

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "subscription_type", updatable = false, insertable = false)
	private Subscriptions subscription;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "extra_charges_type", updatable = false, insertable = false)
	public Extra_Charges extra_charges;

	public Clients() {
	}

	public Clients(String name, String address, double balance, String phone) {
		this.setName(name);
		this.setAddress(address);
		this.setBalance(balance);
		this.setPhone(phone);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public double getBalance() {
		return Balance;
	}

	public void setBalance(double balance) {
		Balance = balance;
	}

	public int getSubscriptionType() {
		return SubscriptionType;
	}

	public void setSubscriptionType(int subscriptionType) {
		SubscriptionType = subscriptionType;
	}
	
	

	public int getExtraChargesType() {
		return ExtraChargesType;
	}

	public void setExtraChargesType(int extraChargesType) {
		ExtraChargesType = extraChargesType;
	}

	public Extra_Charges getExtra_charges() {
		return extra_charges;
	}

	public void setExtra_charges(Extra_Charges extra_charges) {
		this.extra_charges = extra_charges;
	}

	public Subscriptions getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscriptions subscription) {
		this.subscription = subscription;
	}

	public CallsHistory getCallHistory() {
		return callHistory;
	}

	public void setCallHistory(CallsHistory callHistory) {
		this.callHistory = callHistory;
	}

	public SmsHistory getSmsHistory() {
		return smsHistory;
	}

	public void setSmsHistory(SmsHistory smsHistory) {
		this.smsHistory = smsHistory;
	}

	public NetworkHistory getNetworkHistory() {
		return networkHistory;
	}

	public void setNetworkHistory(NetworkHistory networkHistory) {
		this.networkHistory = networkHistory;
	}

}
