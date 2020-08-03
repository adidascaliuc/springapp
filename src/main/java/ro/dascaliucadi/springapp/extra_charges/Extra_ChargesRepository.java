package ro.dascaliucadi.springapp.extra_charges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Extra_ChargesRepository extends JpaRepository<Extra_Charges, Integer>{

}
