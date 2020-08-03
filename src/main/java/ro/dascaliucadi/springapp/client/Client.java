package ro.dascaliucadi.springapp.client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import ro.dascaliucadi.springapp.enumerari.SubscriptionEnum;
import ro.dascaliucadi.springapp.subscription.Subscription;
import ro.dascaliucadi.springapp.subscription.SubscriptionRepository;

@Entity
@Table(name="client")
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@NotNull
	private int ID;
	
	@NotNull
	private String Name;
	
	@NotNull
	private String Address;
	
	@NotNull
	private String Phone;
	
	@NotNull
	private double Balance;
	
	@Transient
	private final SubscriptionRepository subscriptionRepository;
	
	@Transient
	private Subscription sub = new Subscription(SubscriptionEnum.standard, this);
	
	public Client() {
		this.subscriptionRepository = null; }
	public Client(String name, String address, double balance, String phone) { 
		this.subscriptionRepository = null;
		this.setName(name);
		this.setAddress(address);
		this.setBalance(balance);
		this.setPhone(phone);
		
		//subscriptionRepository.save(sub);	
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
	
}
