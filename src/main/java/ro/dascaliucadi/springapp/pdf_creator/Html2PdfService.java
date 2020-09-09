package ro.dascaliucadi.springapp.pdf_creator;

import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

@Service
public interface Html2PdfService {

	InputStreamResource generateInvoice(Map<String, Object> data);
	
}
