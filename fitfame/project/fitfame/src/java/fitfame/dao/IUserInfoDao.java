package fitfame.dao;

import fitfame.po.UserInfo;

public interface IUserInfoDao {
	
	public UserInfo getUserInfoByUid(String uid);
	
	public int insertUserInfo(UserInfo info);
	
	public int updateUserInfo(UserInfo info);
	
	public int deleteUserInfo(UserInfo info);
}
