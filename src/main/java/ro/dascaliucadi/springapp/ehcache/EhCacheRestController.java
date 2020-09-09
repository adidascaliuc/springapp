package ro.dascaliucadi.springapp.ehcache;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/ehcache")
public class EhCacheRestController {

	private final static Logger log = Logger.getLogger(EhCacheRestController.class.getName());
	
	@Autowired
    private NumberService numberService;
 
    @GetMapping(path = "/square/{number}")
    public String getSquare(@PathVariable Long number) {
        log.info("call numberService to square {}" + number);
        return String.format("{\"square\": %s}", numberService.square(number));
    }
    
    @GetMapping(path = "/square2/{number}")
    public String getSquare2(@PathVariable Long number) {
        log.info("call numberService2 to square {}" + number);
        return String.format("{\"square2\": %s}", numberService.square2(number));
    }
}
