package ro.dascaliucadi.springapp.servicies.simulation_history;

import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.simulation_history.CDR;
import ro.dascaliucadi.springapp.simulation_history.CDRRepository;

@Service
public class CDRServiciesImpl implements CDRServicies {
	
	public final CDRRepository cdrRepository;
	
	public CDRServiciesImpl(CDRRepository cdrRepository) {
		this.cdrRepository = cdrRepository;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<CDR> getAllCdrs() {
		return cdrRepository.findAll();
	}
	
	@Override
	public CDR getCdrByClientId(int id) {
		
		return cdrRepository.findById(id).get();
	}
}
