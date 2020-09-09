package ro.dascaliucadi.springapp.quartz_job;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.extra_charges.Extra_Charges;
import ro.dascaliucadi.springapp.properties.Properties;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;

@Component
@PropertySource(value = "classpath:/config.properties")
public class QuartzTest extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		ClientsServicies clientServicies = (ClientsServicies) context.getMergedJobDataMap().get("clientServicies");

		ApplicationContext cont = new ClassPathXmlApplicationContext("my-beans.xml");
		Properties prop = cont.getBean("properties", Properties.class);

		Extra_ChargesServicies extra_chargesServicies = (Extra_ChargesServicies) context.getMergedJobDataMap()
				.get("extra_chargesServicies");

		List<Clients> allClients = clientServicies.getAllClients();

		for (Clients client : allClients) {
			try {
				File file = new File(prop.getDirectoryName() + "/" + client.getID() + "_"
						+ client.getName().replace(" ", "_") + "." + prop.getFileExtension());

				FileWriter fileWriter = new FileWriter(file);

				fileWriter.write("ID: " + client.getID());
				fileWriter.write("\nName: " + client.getName());
				fileWriter.write("\nAddress: " + client.getAddress());
				fileWriter.write("\nBalance: " + client.getBalance());
				fileWriter.write("\nPhone: " + client.getPhone());
				fileWriter
						.write("\nSubscription Type: " + (client.getSubscriptionType() == 1 ? "Standard" : "Premium"));
				fileWriter
						.write("\nExtra Charges Type: " + (client.getExtraChargesType() == 1 ? "Standard" : "Premium"));

				fileWriter.write("\n\nExtra Charges: ");
				List<Extra_Charges> extraChargesClientCurrent = extra_chargesServicies
						.getAllExtra_ChargesByClientIdForPreviousMonth(client.getID());

				for (Extra_Charges extra : extraChargesClientCurrent) {
					fileWriter.write("\n\tCall: " + extra.getCall() + " $");
					fileWriter.write("\n\tNetwork Call: " + extra.getNetworkCall() + " $");
					fileWriter.write("\n\tSms: " + extra.getSMS() + " $");
					fileWriter.write("\n\tNetwork Sms: " + extra.getNetworkSMS() + " $");
					fileWriter.write("\n\tnternet Traffic: " + extra.getInternetTraffic() + " $");
				}
				fileWriter.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Generated invoice at: " + new Date());
	}
}
