/**
 * 
 */
package fitfame.dao;

import fitfame.po.UserPassport;

/**
 * @author zhangshu
 *
 */
public interface IUserPassportDao {

	public String checkUserPassport(String tel, String pw);
	
	public UserPassport getUserPassport(String uid);
	
	public String ifUserPassportExist(String tel);
	
	public int insertUserPassport(UserPassport info);
	
	public int updateUserPassport(UserPassport info);
}
