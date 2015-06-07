package fitfame.dao;

import fitfame.po.AdminInfo;

public interface IAdminInfoDao {
	
	public AdminInfo getAdminInfo(AdminInfo info);
	
	public int insertAdminInfo(AdminInfo info);
	
	public int updateAdminInfo(AdminInfo info);
	
	public int deleteAdminInfo(AdminInfo info);
}