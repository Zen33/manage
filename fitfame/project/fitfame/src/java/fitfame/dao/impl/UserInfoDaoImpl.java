package fitfame.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IUserInfoDao;
import fitfame.po.UserInfo;

@Repository
public class UserInfoDaoImpl extends BaseDAO<UserInfo> implements IUserInfoDao{

	@Override
	public UserInfo getUserInfoByUid(String uid) {
		UserInfo info = null;
		try{
			info = (UserInfo) this.getSqlMapClientTemplate().queryForObject(
					"UserInfo.getUserInfoByUid", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return info;
	}

	@Override
	public int insertUserInfo(UserInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("UserInfo.insertUserInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormUserInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int updateUserInfo(UserInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("UserInfo.updateUserInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormUserInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int deleteUserInfo(UserInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("UserInfo.deleteUserInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormUserInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
