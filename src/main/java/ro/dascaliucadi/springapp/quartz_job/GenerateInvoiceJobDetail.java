package ro.dascaliucadi.springapp.quartz_job;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;

@Component
public class GenerateInvoiceJobDetail extends JobDetailFactoryBean {

	@Autowired
	private ClientsServicies clientServicies;
	
	@Autowired
	private Extra_ChargesServicies extra_chargesServicies;

	@Override
	public void afterPropertiesSet() {
		setJobClass(QuartzTest.class);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("clientServicies", clientServicies);
		data.put("extra_chargesServicies", extra_chargesServicies);
		setJobDataAsMap(data);
		setDurability(true);
		super.afterPropertiesSet();
	}
}
