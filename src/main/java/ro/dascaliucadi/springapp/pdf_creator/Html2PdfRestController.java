package ro.dascaliucadi.springapp.pdf_creator;

import java.text.DateFormatSymbols;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.dascaliucadi.springapp.clients.Clients;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
import ro.dascaliucadi.springapp.servicies.extra_charges.Extra_ChargesServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.CallsServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.NetworkServicies;
import ro.dascaliucadi.springapp.servicies.simulation_history.SmsServicies;

@RestController
@RequiredArgsConstructor
public class Html2PdfRestController {

	private final Html2PdfService pdfService;

	public Html2PdfRestController(Html2PdfService pdfService) {
		this.pdfService = pdfService;
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private ClientsServicies clientServicies;
	
	@Autowired
	private Extra_ChargesServicies extra_chargesServicies;

	@Autowired
	private CallsServicies callServicies;

	@Autowired
	private SmsServicies smsServicies;

	@Autowired
	private NetworkServicies networkServicies;

	@PostMapping(value = "/invoice", produces = "application/pdf")
	public ResponseEntity<InputStreamResource> html2pdf(@ModelAttribute("client") Clients client, Model model, Map<String, Object> data) {
		int id = clientServicies.getByPhoneNumber(client.getPhone()).getID();
		
		LocalDate localDate = new LocalDate();
		String payMonth = "01-" + localDate.toString("MMMM")+ "-" + localDate.getYear();
		String dueMonth = "01-"  + new DateFormatSymbols().getMonths()[localDate.getMonthOfYear()] + "-" + localDate.getYear();
		
		model.addAttribute("payMonth", payMonth);
		model.addAttribute("dueMonth", dueMonth);
		
		model.addAttribute("extra_charges", extra_chargesServicies.getAllExtra_ChargesByClientId(id));
		model.addAttribute("sub", clientServicies.findClientByID(id).getSubscription());
		model.addAttribute("client", clientServicies.findClientByID(id));
		model.addAttribute("calls", callServicies.getAllCallsByClientId(id));
		model.addAttribute("smss", smsServicies.getAllSmsById(id));
		model.addAttribute("networks", networkServicies.getAllNetworkById(id));

		InputStreamResource resource = pdfService.generateInvoice(data);

		if (resource != null) {
			return ResponseEntity.ok().body(resource);
		} else {
			return new ResponseEntity<InputStreamResource>(HttpStatus.SERVICE_UNAVAILABLE);
		}

	}
}
