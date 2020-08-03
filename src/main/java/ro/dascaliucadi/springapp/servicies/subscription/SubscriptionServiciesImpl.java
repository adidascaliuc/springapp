package ro.dascaliucadi.springapp.servicies.subscription;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.subscription.Subscription;
import ro.dascaliucadi.springapp.subscription.SubscriptionRepository;

@Service
public class SubscriptionServiciesImpl implements SubscriptionServicies{
	
	private final SubscriptionRepository subscriptionRepository;
	
	public SubscriptionServiciesImpl(SubscriptionRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Subscription> getAllSubscriptions() {
		List<Subscription> subscriptions = new ArrayList<Subscription>();
		for(Subscription sub : subscriptionRepository.findAll()) {
			subscriptions.add(sub);
		}
		return subscriptions;
	}
}
