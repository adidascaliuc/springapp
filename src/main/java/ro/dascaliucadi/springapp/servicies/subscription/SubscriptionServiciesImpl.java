package ro.dascaliucadi.springapp.servicies.subscription;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.subscription.Subscriptions;
import ro.dascaliucadi.springapp.subscription.SubscriptionsRepository;

@Service
@Configurable
public class SubscriptionServiciesImpl implements SubscriptionServicies{
	
	@Autowired
	private SubscriptionsRepository subscriptionRepository;
	
	@Override
	public List<Subscriptions> getAllSubscriptions() {
		List<Subscriptions> subscriptions = new ArrayList<Subscriptions>();
		for(Subscriptions sub : subscriptionRepository.findAll()) {
			subscriptions.add(sub);
		}
		return subscriptions;
	}
}
