package ro.dascaliucadi.springapp.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class PropertiesWithJavaConfig {
    //...
}
