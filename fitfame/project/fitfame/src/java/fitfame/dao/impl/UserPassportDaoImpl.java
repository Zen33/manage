/**
 * 
 */
package fitfame.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IUserPassportDao;
import fitfame.po.UserPassport;

/**
 * @author zhangshu
 *
 */
@Repository
public class UserPassportDaoImpl extends BaseDAO<UserPassport> implements
		IUserPassportDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IUserPassportDao#checkUserPassport(java.lang.String, java.lang.String)
	 */
	@Override
	public String checkUserPassport(String tel, String pw) {
		// TODO Auto-generated method stub
		String result = null; 
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tel", tel);
		parameter.put("pw", pw);
		try{
			result = (String) this.getSqlMapClientTemplate().queryForObject(
					"UserPassport.checkUserPassport", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "checkUserPassport error tel:"+tel);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IUserPassportDao#insertUserPassport(fitfame.po.UserPassport)
	 */
	@Override
	public int insertUserPassport(UserPassport info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("UserPassport.insertUserPassport", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertUserPassport error"+SqlErrorUtil.FormUserPassportLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;

	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IUserPassportDao#updateUserPassport(fitfame.po.UserPassport)
	 */
	@Override
	public int updateUserPassport(UserPassport info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("UserPassport.updateUserPassport", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateUserPassport error"+SqlErrorUtil.FormUserPassportLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public String ifUserPassportExist(String tel) {
		// TODO Auto-generated method stub
		String result = null; 
		try{
			result = (String) this.getSqlMapClientTemplate().queryForObject(
					"UserPassport.ifUserPassportExist", tel);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "ifUserPassportExist error tel:"+tel);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public UserPassport getUserPassport(String uid) {
		UserPassport result = null; 
		try{
			result = (UserPassport) this.getSqlMapClientTemplate().queryForObject(
					"UserPassport.getUserPassport", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getUserPassport error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
