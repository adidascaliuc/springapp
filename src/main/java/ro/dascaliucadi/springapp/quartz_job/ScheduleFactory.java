package ro.dascaliucadi.springapp.quartz_job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ScheduleFactory extends SchedulerFactoryBean {

	@Autowired
	private GenerateInvoiceJobDetail jobDetailFactory;

	@Autowired
	private GenerateInvoiceTriggerFactory triggerFactory;

	@Override
	public void afterPropertiesSet() throws Exception {
		setJobDetails(jobDetailFactory.getObject());
		setTriggers(triggerFactory.getObject());
		super.afterPropertiesSet();
	}
}
