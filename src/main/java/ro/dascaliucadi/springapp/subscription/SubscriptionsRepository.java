package ro.dascaliucadi.springapp.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscriptions, Integer>{

}
