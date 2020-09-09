package ro.dascaliucadi.springapp.ehcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class EhCacheConfiguration {

//	@Bean
//	public CacheManager cacheManager() {
//		return new EhCacheCacheManager(cacheManagerFactory().getObject());
//	}
//	
//	@Bean
//	private EhCacheManagerFactoryBean cacheManagerFactory() {
//		EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
//		bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//		bean.setShared(true);
//		return bean;
//	}
}
