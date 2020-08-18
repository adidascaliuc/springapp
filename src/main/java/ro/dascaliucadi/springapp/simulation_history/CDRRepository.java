package ro.dascaliucadi.springapp.simulation_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CDRRepository extends JpaRepository<CDR, Integer> {

}
