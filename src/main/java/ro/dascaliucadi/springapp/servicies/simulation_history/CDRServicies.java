package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.simulation_history.CDR;

@Service
public interface CDRServicies {
	List<CDR> getAllCdrs();
	CDR getCdrByClientId(int id);
}
