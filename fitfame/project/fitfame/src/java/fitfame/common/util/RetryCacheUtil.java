package fitfame.common.util;

import fitfame.common.cache.MemcachedCache;
import java.util.LinkedList;
import java.util.Queue;

import org.jboss.resteasy.plugins.spring.SpringContextLoader;

public class RetryCacheUtil {

	//默认key cache_size 1024 * 1024 * 500
	private static long CacheSize = 0;
	
	private static final long MaxCacheSize = 1024 * 1024 * 64;
	
	//默认key cache_queue
	private static Queue<String> CacheQueue = null;
	
	private static MemcachedCache memcachedCache = (MemcachedCache)SpringContextLoader.getCurrentWebApplicationContext().getBean("memcachedCache");
	
	@SuppressWarnings("unchecked")
	private static void initCache()
	{
		if(CacheSize == 0)
		{
			String memKey = MD5Util.toMD5Str("cache_size");
			if(memcachedCache.get(memKey) != null)
				CacheSize = (Long) memcachedCache.get(memKey);
			memKey = MD5Util.toMD5Str("cache_queue");
			CacheQueue = (Queue<String>) memcachedCache.get(memKey);
			if(CacheQueue == null)
				CacheQueue = new LinkedList<String>();			
		}
	}
	
	public static synchronized void AddRetryCache(String key, String value)
	{
		if(key == null)
			return ;
		if(value.length() > MaxCacheSize)
			return ;
		initCache();
		//先判断hash中有没有该值
		String memKey = MD5Util.toMD5Str(key);
		
		if(memcachedCache.get(memKey) != null)
		{
			//hash中已存在
			memcachedCache.remove(memKey);
			memcachedCache.add(memKey, value);
		}
		else
		{
			//hash中不存在
			//判断是否超出缓存池范围
			while(CacheSize + value.length() > MaxCacheSize)
			{
				String index = CacheQueue.poll();
				if(index == null)
				{
	        		LogUtil.WriteLog(ExceptionIdUtil.OverCacheSize,CacheSize + ":" + CacheQueue.toString());
	        		return ;
	        		//throw new BaseServiceException(ExceptionIdUtil.OverCacheSize,uid + ":" + Ecount + ":" + pack.getBagUpper());
				}
				String index_value = (String) memcachedCache.get(MD5Util.toMD5Str(index));
				CacheSize -= index_value.length();
				memcachedCache.remove(MD5Util.toMD5Str(index));
			}
			memcachedCache.add(memKey, value);
			CacheSize += value.length();
			CacheQueue.offer(key);
			memKey = MD5Util.toMD5Str("cache_size");
			memcachedCache.remove(memKey);
			memcachedCache.add(memKey, CacheSize);
			
			memKey = MD5Util.toMD5Str("cache_queue");
			memcachedCache.remove(memKey);
			memcachedCache.add(memKey, CacheQueue);
		}
	}
	
	public static String GetRetryCache(String key)
	{
		if(key == null)
			return null;
		String memKey = MD5Util.toMD5Str(key);
		String json = (String) memcachedCache.get(memKey);
		return json;
	}
}
