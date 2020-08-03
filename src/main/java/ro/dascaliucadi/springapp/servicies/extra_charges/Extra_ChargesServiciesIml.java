package ro.dascaliucadi.springapp.servicies.extra_charges;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.extra_charges.Extra_ChargesRepository;

@Service
public class Extra_ChargesServiciesIml implements Extra_ChargesServicies{

	private final Extra_ChargesRepository extra_chargesRepository;
	
	public Extra_ChargesServiciesIml(Extra_ChargesRepository extra_chargesRepository) {
		this.extra_chargesRepository = extra_chargesRepository;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Extra_Charges> getAllExtra_Charges() {
		List<Extra_Charges> extra_chargeses = new ArrayList<Extra_Charges>();
		for(Extra_Charges extra : extra_chargesRepository.findAll()) {
			extra_chargeses.add(extra);
		}
		return extra_chargeses;
	}
}
