package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.simulation_history.CDR;
import ro.dascaliucadi.springapp.simulation_history.CDRRepository;

@Service
@Configurable
public class CDRServiciesImpl implements CDRServicies {
	
	@Autowired
	public CDRRepository cdrRepository;

	
	@Override
	public List<CDR> getAllCdrs() {
		return cdrRepository.findAll();
	}
	
	@Override
	public CDR getCdrByClientId(int id) {
		
		return cdrRepository.findById(id).get();
	}
}
