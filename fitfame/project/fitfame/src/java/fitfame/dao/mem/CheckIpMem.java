package fitfame.dao.mem;

import fitfame.common.cache.MemcachedCache;
import fitfame.common.cache.SingletonMemcached;
import fitfame.common.util.DateUtil;
import fitfame.common.util.MD5Util;

public class CheckIpMem {

	public static boolean CheckIp(String ip)
	{
		MemcachedCache memcachedCache = SingletonMemcached.getSingletonMemcached();
		String memKey = MD5Util.toMD5Str("ipTimer" + ip);
		Integer count = (Integer)memcachedCache.get(memKey);
		if(count == null)
		{
			count = new Integer(20);
			memcachedCache.add(memKey, count, DateUtil.SuperHighLevelDate());
			memKey = MD5Util.toMD5Str("ipCount" + ip);
			memcachedCache.remove(memKey);
			memcachedCache.add(memKey, count, DateUtil.SuperHighLevelDate());
			return true;
		}
		else
		{
			memKey = MD5Util.toMD5Str("ipCount" + ip);
			count = (Integer)memcachedCache.get(memKey);
			if(count == null || count < 1)
				return false;
			memcachedCache.replace(memKey, count -- );
			return true;
		}
	}
}
