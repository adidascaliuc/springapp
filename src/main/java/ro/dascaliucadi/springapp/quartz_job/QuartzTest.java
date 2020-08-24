package ro.dascaliucadi.springapp.quartz_job;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {
	public static void main(String[] args) throws SchedulerException {

		JobDetail job = JobBuilder.newJob(GenerateInvoiceJob.class).build();
		
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(02)
						.repeatForever()).build();
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
		
	}

}
