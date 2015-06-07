/**
 * 
 */
package fitfame.common.util;

import fitfame.common.cache.MemcachedCache;
import fitfame.common.cache.SingletonMemcached;

/**
 * @author zhangshu
 *
 */
public class TokenUtil {
	
	public static String produceToken(String uid){
		MemcachedCache memcachedCache = SingletonMemcached.getSingletonMemcached();
		String token = MD5Util.toMD5Str(GenerateSequenceUtil.generateSequenceNo());
		memcachedCache.set(token, uid, DateUtil.SuperHighLevelDate());
		return token;
	}
	
	public static String checkToken(String token){
		MemcachedCache memcachedCache = SingletonMemcached.getSingletonMemcached();
		if(memcachedCache.get(token) == null)
		    return null;
		return (String)memcachedCache.get(token);
	}

}
