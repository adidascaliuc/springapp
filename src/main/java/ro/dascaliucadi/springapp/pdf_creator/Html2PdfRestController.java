package ro.dascaliucadi.springapp.pdf_creator;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.dascaliucadi.springapp.servicies.client.ClientsServicies;
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
	private CallsServicies callServicies;

	@Autowired
	private SmsServicies smsServicies;

	@Autowired
	private NetworkServicies networkServicies;

	@RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity html2pdf(@PathVariable int id, Model model, Map<String, Object> data) {

		LocalDate localDate = new LocalDate();
		String payMonth = "01-" + localDate.toString("MMMM")+ "-" + localDate.getYear();
		String dueMonth = "01-"  + new DateFormatSymbols().getMonths()[localDate.getMonthOfYear()] + "-" + localDate.getYear();
		
		model.addAttribute("payMonth", payMonth);
		model.addAttribute("dueMonth", dueMonth);
		
		model.addAttribute("extra_charges", clientServicies.findClientByID(id).getExtra_charges());
		model.addAttribute("sub", clientServicies.findClientByID(id).getSubscription());
		model.addAttribute("client", clientServicies.findClientByID(id));
		model.addAttribute("calls", callServicies.getAllCallsById(id));
		model.addAttribute("smss", smsServicies.getAllSmsById(id));
		model.addAttribute("networks", networkServicies.getAllNetworkById(id));

		InputStreamResource resource = pdfService.generateInvoice(data);

		if (resource != null) {
			return ResponseEntity.ok().body(resource);
		} else {
			return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
		}

	}
}
