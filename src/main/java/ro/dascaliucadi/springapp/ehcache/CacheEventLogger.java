package ro.dascaliucadi.springapp.ehcache;

import java.util.logging.Logger;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger implements CacheEventListener<Object, Object> {

	private Logger log = Logger.getLogger(CacheEventLogger.class.getName());

	@Override
  public void onEvent(
    CacheEvent<? extends Object, ? extends Object> cacheEvent) {
      log.info("CacheEventLogger: " +
        cacheEvent.getKey() + cacheEvent.getOldValue() + cacheEvent.getNewValue());
  }
}
