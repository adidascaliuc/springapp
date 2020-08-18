package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import ro.dascaliucadi.springapp.simulation_history.CDR;

public interface CDRServicies {
	List<CDR> getAllCdrs();
	CDR getCdrByClientId(int id);
}
