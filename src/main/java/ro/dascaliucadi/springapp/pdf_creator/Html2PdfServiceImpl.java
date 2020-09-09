package ro.dascaliucadi.springapp.pdf_creator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.HtmlConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Service
@RequiredArgsConstructor
@Log
public class Html2PdfServiceImpl implements Html2PdfService {

	@Autowired
	private TemplateEngine templateEngine;
	
	@Override
	public InputStreamResource generateInvoice(Map<String, Object> data) {
		Context context = new Context();
		context.setVariables(data);
		String html = templateEngine.process("invoicePDF", context);

		try {
			HtmlConverter.convertToPdf(html, new FileOutputStream("target/test.pdf"));
			return new InputStreamResource(new FileInputStream("target/test.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
