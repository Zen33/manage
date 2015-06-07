package fitfame.dao.mem;

import org.jboss.resteasy.plugins.spring.SpringContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import fitfame.common.cache.SingletonMemcached;

import fitfame.common.cache.MemcachedCache;
import fitfame.common.util.DateUtil;
import fitfame.common.util.MD5Util;
import fitfame.dao.IUserInfoDao;
import fitfame.po.UserInfo;

@Repository
public class UserInfoDaoMem implements IUserInfoDao{
	@Autowired(required = true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;

	@Override
	public UserInfo getUserInfoByUid(String uid) {
		UserInfo info = null;
		MemcachedCache memcachedCache = SingletonMemcached.getSingletonMemcached();
		String memKey = MD5Util.toMD5Str("userInfoByUid" + uid);
		info = (UserInfo)memcachedCache.get(memKey);
		if(info == null)
		{
			info = userInfoDaoImpl.getUserInfoByUid(uid);
			if(info != null)
			{
			    memcachedCache.add(memKey, info, DateUtil.MiddleLevelDate());
			}
		}
		return info;
	}

	@Override
	public int insertUserInfo(UserInfo info) {
		userInfoDaoImpl.insertUserInfo(info);
		return 0;
	}

	@Override
	public int updateUserInfo(UserInfo info) {		
		MemcachedCache memcachedCache = SingletonMemcached.getSingletonMemcached();
		String memKey = MD5Util.toMD5Str("userInfoByUid" + info.getUid());
		memcachedCache.remove(memKey);
//		memKey = MD5Util.toMD5Str("userInfoByTel" + info.getTel());
		memcachedCache.remove(memKey);
		userInfoDaoImpl.updateUserInfo(info);
		return 0;
	}

	@Override
	public int deleteUserInfo(UserInfo info) {		
		MemcachedCache memcachedCache = (MemcachedCache)SpringContextLoader.getCurrentWebApplicationContext().getBean("memcachedCache");
		String memKey = MD5Util.toMD5Str("userInfoByUid" + info.getUid());
		memcachedCache.remove(memKey);
//		memKey = MD5Util.toMD5Str("userInfoByTel" + info.getTel());
		memcachedCache.remove(memKey);
		userInfoDaoImpl.deleteUserInfo(info);
		return 0;
	}

}
