package ro.dascaliucadi.springapp.quartz_job;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class GenerateInvoiceTriggerFactory extends CronTriggerFactoryBean {

	@Autowired
	private GenerateInvoiceJobDetail jobDetailFactory;

	@Override
	public void afterPropertiesSet() throws ParseException {
		setCronExpression("0 * * ? * *");
		setJobDetail(jobDetailFactory.getObject());
		super.afterPropertiesSet();
	}

}
