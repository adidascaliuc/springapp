package ro.dascaliucadi.springapp.controller.simulation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Sms;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.SmsServicies;
import ro.dascaliucadi.springapp.simulation_history.SmsHistory;

@Controller
public class SmsController {

	private Clients clientOneSms = null;
	private Clients clientTwoSms = null;

	@Autowired
	private final ClientsServicies clientServicies;

	@Autowired
	private final SmsServicies smsServicies;

	public SmsController(ClientsServicies clientServicies, SmsServicies smsServicies) {
		this.clientServicies = clientServicies;
		this.smsServicies = smsServicies;
	}

	@GetMapping("/simulate/message")
	public String simulateMessage(Model model) {
		Clients client = new Clients();

		model.addAttribute("client", client);
		model.addAttribute("clients", clientServicies.findAllClients());

		return "message";
	}

	@PostMapping("/simulate/message")
	public String messageResponse(@ModelAttribute("client") Clients client) {

		String firstNumber = client.getPhone().split(",")[0];
		String secondNumber = client.getPhone().split(",")[1];
		
		clientOneSms = clientServicies.findClientByPhone(firstNumber);
		clientOneSms.setSmsHistory(new SmsHistory());
		
		if (firstNumber.substring(0, 4).equals(secondNumber.substring(0, 4))) {
			clientOneSms.getSmsHistory().setSmsType(String.valueOf(Sms.in_network));
		} else {
			clientOneSms.getSmsHistory().setSmsType(String.valueOf(Sms.outside_network));
		}

		clientOneSms = clientServicies.findClientByPhone(firstNumber);
		clientTwoSms = clientServicies.findClientByPhone(secondNumber);

		clientOneSms.getSmsHistory().setDateSmsSent();

		SmsHistory smsClientCurrent = new SmsHistory();
		for (SmsHistory sms : smsServicies.getAllSms()) {
			if (sms.getClientId() == clientOneSms.getID()) {
				smsClientCurrent = sms;
				break;
			}
		}
		smsClientCurrent.setNrOfSms(1);

		smsServicies.addSms(clientOneSms, clientTwoSms.getPhone(), clientOneSms.getSmsHistory().getDateSmsSent(),
				smsClientCurrent.getNrOfSms(), clientOneSms.getSmsHistory().getSmsType());
		
		clientServicies.updateClient(clientOneSms);

		return "homepage";
	}

	@GetMapping("/history/message")
	public String historyMessage(Model model) {

		model.addAttribute("allSms", smsServicies.getAllSms());

		return "history_message";
	}

}
