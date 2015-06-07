package fitfame.dao;

import fitfame.po.VerifyInfo;

public interface IVerifyInfoDao {

	public VerifyInfo getVerifyInfo(String phone);
	
	public int insertVerifyInfo(VerifyInfo info);
	
	public int updateVerifyInfo(VerifyInfo info);
	
	public int updateVerifyAll(int verifyCount, int pwCount);
	
	public int deleteVerifyInfo(String phone);
}
