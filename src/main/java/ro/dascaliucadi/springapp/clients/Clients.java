package ro.dascaliucadi.springapp.clients;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.sun.istack.NotNull;

import ro.dascaliucadi.springapp.enumerari.SubscriptionsEnum;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.subscription.Subscriptions;
import ro.dascaliucadi.springapp.subscription.SubscriptionsRepository;

@Entity
@Table(name="CLIENTS" )
public class Clients {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	@Column(name="id", unique=true)
	private int ID;
	
	@NotNull
	@Column(name="name", unique=true)
	private String Name;
	
	@NotNull
	@Column(name="address", unique=true)
	private String Address;
	
	@NotNull
	@Column(name="phone" ,unique=true)
	private String Phone;
	
	@NotNull
	@Column(name="balance", unique=true)
	private double Balance;
	
	@NotNull
	@Column(name="subscription_type", unique=true)
	private String SubscriptionType;
	
	@NotNull
	@Column(name="extra_charges_type", unique=true)
	private String Extra_ChargesType;
	
	@Transient
	private String User;
	
	@Transient
	private String Token;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "extra_charges_type", updatable=false, insertable=false)
	private Subscriptions subscription;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "subscription_type", updatable=false, insertable=false)
	private Extra_Charges extra_charges;
	
	public Clients() { }
	public Clients(String name, String address, double balance, String phone) { 
		this.setName(name);
		this.setAddress(address);
		this.setBalance(balance);
		this.setPhone(phone);
	}
	
	
	
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
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
	public String getSubscriptionType() {
		return SubscriptionType;
	}
	public void setSubscriptionType(String subscriptionType) {
		SubscriptionType = subscriptionType;
	}
	public String getExtra_ChargesType() {
		return Extra_ChargesType;
	}
	public void setExtra_ChargesType(String extra_ChargesType) {
		Extra_ChargesType = extra_ChargesType;
	}
	
	
}
