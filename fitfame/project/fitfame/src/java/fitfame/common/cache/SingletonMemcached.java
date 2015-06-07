/**
 * 
 */
package fitfame.common.cache;

import org.jboss.resteasy.plugins.spring.SpringContextLoader;

/**
 * @author zhangshu
 *
 */
public class SingletonMemcached {

	private static MemcachedCache instance = (MemcachedCache)SpringContextLoader.getCurrentWebApplicationContext().getBean("memcachedCache");

	public static MemcachedCache getSingletonMemcached(){
		return instance;
	}
}
