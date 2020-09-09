package ro.dascaliucadi.springapp.quartz_job;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;

public class SaveFileInvoice {
	
	public void saveFileInvoices(ClientsServicies clientServicies) {
		List<Clients> allClients = clientServicies.getAllClients();
		
		for(Clients client : allClients) {
			try {
				FileWriter fileWriter = new FileWriter(client.getName());
				fileWriter.write("ID: " + client.getID());
				fileWriter.write("Name: " + client.getName());
				fileWriter.write("Address: " + client.getAddress());
				fileWriter.write("Balance: " + client.getBalance());
				fileWriter.write("Phone: " + client.getPhone());
				fileWriter.write("Subscription Type: " + client.getSubscriptionType());
				fileWriter.write("Extra Charges Type: " + client.getExtraChargesType());
				
				fileWriter.write("Extra Charges: ");
//				List<Extra_Charges> extraChargesClientCurrent = 
//						extra_chargesServicices.getAllExtra_ChargesByClientId(client.getID());
//				
//				for(Extra_Charges extra : extraChargesClientCurrent) {
//					fileWriter.write("Call: " + extra.getCall());
//					fileWriter.write("Network Call: " + extra.getNetworkCall());
//					fileWriter.write("Sms: " + extra.getSMS());
//					fileWriter.write("Network Sms: " + extra.getNetworkSMS());
//					fileWriter.write("Internet Traffic: " + extra.getInternetTraffic());					
//				}
				fileWriter.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

}
