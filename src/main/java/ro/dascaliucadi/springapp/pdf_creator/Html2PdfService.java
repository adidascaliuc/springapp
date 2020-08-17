package ro.dascaliucadi.springapp.pdf_creator;

import java.util.Map;

import org.springframework.core.io.InputStreamResource;

public interface Html2PdfService {

	InputStreamResource generateInvoice(Map<String, Object> data);
	
}
