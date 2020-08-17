package ro.dascaliucadi.springapp.simulation_history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NetworkRepository extends JpaRepository<NetworkHistory, Integer> {

}
