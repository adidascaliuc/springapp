package ro.dascaliucadi.springapp.controller.simulation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.enumerari.Call;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.CallsServicies;
import ro.dascaliucadi.springapp.simulation_history.CallsHistory;

@Controller
public class PhoneCallController {

	private Clients clientOnePhone = null;
	private Clients clientTwoPhone = null;

	private Date dateStart = null;
	private Date dateEnd = null;

	@Autowired
	private final CallsServicies callsServicies;

	@Autowired
	private final ClientsServicies clientServicies;

	public PhoneCallController(CallsServicies callsServicies, ClientsServicies clientServicies) {
		this.callsServicies = callsServicies;
		this.clientServicies = clientServicies;

	}

	@GetMapping("/simulate/phone-call")
	public String phoneCall(Model model) {
		Clients client = new Clients();

		model.addAttribute("client", client);
		model.addAttribute("clients", clientServicies.findAllClients());

		return "phone_call";
	}

	@PostMapping("/simulate/phone-call")
	public String phoneCallResponse(@ModelAttribute("client") Clients client) {
		client.setCallHistory(new CallsHistory());

		client.getCallHistory().setStartCall();

		String firstNumber = client.getPhone().split(",")[0];
		String secondNumber = client.getPhone().split(",")[1];

		clientOnePhone = clientServicies.findClientByPhone(firstNumber);
		clientOnePhone.setCallHistory(new CallsHistory());

		clientTwoPhone = clientServicies.findClientByPhone(secondNumber);

		clientOnePhone.getCallHistory().setStartCall();

		if (firstNumber.substring(0, 4).equals(secondNumber.substring(0, 4)))

		{
			System.out.println("Same call furnizor");
			clientOnePhone.getCallHistory().setCallType(String.valueOf(Call.in_network));
		} else {
			System.out.println("Don't same call furnizor");
			clientOnePhone.getCallHistory().setCallType(String.valueOf(Call.outside_network));
		}

		return "make_call";
	}

	@PostMapping("/simulate/phone-call/end")
	public String endCall(@ModelAttribute("client") Clients client, Model model) {

		clientOnePhone.getCallHistory().setEndCall();

		try {
			dateStart = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
					.parse(clientOnePhone.getCallHistory().getStartCall());
			dateEnd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(clientOnePhone.getCallHistory().getEndCall());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long minutes = getDateDiff(dateStart, dateEnd, TimeUnit.MINUTES);

		long minSpend = minutes == 0 ? 1 : minutes;

		callsServicies.addCall(clientOnePhone, clientTwoPhone.getPhone(),
				clientOnePhone.getCallHistory().getStartCall(), clientOnePhone.getCallHistory().getEndCall(), minSpend,
				clientOnePhone.getCallHistory().getCallType());
		
		clientServicies.updateClient(clientOnePhone);

		return "homepage";
	}

	@GetMapping("/history/calls")
	public String historyCalls(Model model) {

		model.addAttribute("calls", callsServicies.getAllCalls());
		return "history_calls";
	}

	private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
