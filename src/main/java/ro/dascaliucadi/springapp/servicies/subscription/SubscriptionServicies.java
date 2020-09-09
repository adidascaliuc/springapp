package ro.dascaliucadi.springapp.servicies.subscription;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.subscription.Subscriptions;

@Service
public interface SubscriptionServicies {
	List<Subscriptions> getAllSubscriptions();
}
