package ro.dascaliucadi.springapp.webconfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//  	    http
//  	    .authorizeRequests()//.antMatchers("/mycontroller/**").authenticated()
//  	    .antMatchers("/console/**").permitAll().anyRequest().authenticated().and().formLogin();
//  	    //.loginPage("/login");
//  	    
//  	    //http.addFilterAfter(new SimpleCORSFilter(),ExceptionTranslationFilter.class);
//  	    
//  	    
//  	    http.csrf().disable();
//  	    http.headers().frameOptions().disable();

		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/h2-console/**").permitAll();

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}