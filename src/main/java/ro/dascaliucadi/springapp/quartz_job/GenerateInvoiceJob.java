package ro.dascaliucadi.springapp.quartz_job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import ro.dascaliucadi.springapp.controller.simulation.InvoiceController;

public class GenerateInvoiceJob extends InvoiceController implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		System.out.println("Generate an invoice at: " + new Date());

	}

}
