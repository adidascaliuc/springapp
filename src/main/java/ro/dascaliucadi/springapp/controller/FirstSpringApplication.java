package ro.dascaliucadi.springapp.controller;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;

import ro.dascaliucadi.springapp.JMS.Email;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableOAuth2Sso

@ComponentScan
public class FirstSpringApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FirstSpringApplication.class);
	}

	public static void main(String[] args) throws Exception, SchedulerException {
		//SpringApplication.run(FirstSpringApplication.class, args);
		ConfigurableApplicationContext context = SpringApplication.run(FirstSpringApplication.class, args);
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

	    // Send a message with a POJO - the template reuse the message converter
	    System.out.println("Sending an email message.");
	    jmsTemplate.convertAndSend("mailbox", new Email("adi.dascaliuc1@gmail.com", "Hello"));
	}
}
