package ro.dascaliucadi.springapp.simulation_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<SmsHistory, Integer> {

}
