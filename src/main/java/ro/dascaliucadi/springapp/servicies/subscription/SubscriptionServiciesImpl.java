package ro.dascaliucadi.springapp.servicies.subscription;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.subscription.Subscriptions;
import ro.dascaliucadi.springapp.subscription.SubscriptionsRepository;

@Service
public class SubscriptionServiciesImpl implements SubscriptionServicies{
	
	private final SubscriptionsRepository subscriptionRepository;
	
	public SubscriptionServiciesImpl(SubscriptionsRepository subscriptionRepository) {
		this.subscriptionRepository = subscriptionRepository;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Subscriptions> getAllSubscriptions() {
		List<Subscriptions> subscriptions = new ArrayList<Subscriptions>();
		for(Subscriptions sub : subscriptionRepository.findAll()) {
			subscriptions.add(sub);
		}
		return subscriptions;
	}
}
