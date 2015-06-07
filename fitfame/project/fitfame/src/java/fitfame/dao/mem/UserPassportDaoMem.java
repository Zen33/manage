/**
 * 
 */
package fitfame.dao.mem;

import org.springframework.stereotype.Repository;

import fitfame.dao.IUserPassportDao;
import fitfame.po.UserPassport;

/**
 * @author zhangshu
 *
 */
@Repository
public class UserPassportDaoMem implements IUserPassportDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IUserPassportDao#checkUserPassport(java.lang.String, java.lang.String)
	 */
	@Override
	public String checkUserPassport(String tel, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IUserPassportDao#insertUserPassport(fitfame.po.UserPassport)
	 */
	@Override
	public int insertUserPassport(UserPassport info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IUserPassportDao#updateUserPassport(fitfame.po.UserPassport)
	 */
	@Override
	public int updateUserPassport(UserPassport info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String ifUserPassportExist(String tel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPassport getUserPassport(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
