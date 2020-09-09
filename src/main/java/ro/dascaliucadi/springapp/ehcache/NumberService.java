package ro.dascaliucadi.springapp.ehcache;

import java.math.BigDecimal;
import java.util.logging.Logger;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class NumberService {

	private Logger log = Logger.getLogger(NumberService.class.getName());
	
	@Cacheable(value = "squareCache", key = "#number", condition = "#number>10")
	public BigDecimal square(Long number) {
		BigDecimal square = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(number));
		log.info("square of {" + number + "} is {"+square+"}");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return square;
	}
	
	public BigDecimal square2(Long number) {
		BigDecimal square = BigDecimal.valueOf(number).multiply(BigDecimal.valueOf(number));
		log.info("square of {" + number + "} is {"+square+"}");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return square;
	}
}
