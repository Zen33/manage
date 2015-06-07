/**
 * 
 */
package fitfame.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.page.PageInfo;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IFriendInfoDao;
import fitfame.po.FriendInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class FriendInfoDaoImpl extends BaseDAO<FriendInfo> implements
		IFriendInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#getFriendInfoList(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<FriendInfo> getFriendInfoList(String uid, PageInfo page) {
		// TODO Auto-generated method stub
		List<FriendInfo> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("uid", uid);
		try{
			result = pagedQueryInDB("getFriendInfoList", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getFriendInfoList error uid:"+String.valueOf(uid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#getFriendInfo(fitfame.po.FriendInfo)
	 */
	@Override
	public FriendInfo getFriendInfo(FriendInfo info) {
		// TODO Auto-generated method stub
		FriendInfo result = null;
		try{
			result = (FriendInfo) this.getSqlMapClientTemplate().queryForObject(
					"FriendInfo.getFriendInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getFriendInfo error"+SqlErrorUtil.FormFriendInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#insertFriendInfo(fitfame.po.FriendInfo)
	 */
	@Override
	public int insertFriendInfo(FriendInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("FriendInfo.insertFriendInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertFriendInfo error"+SqlErrorUtil.FormFriendInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#deleteFriendInfo(fitfame.po.FriendInfo)
	 */
	@Override
	public int deleteFriendInfo(FriendInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("FriendInfo.deleteFriendInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteFriendInfo error"+SqlErrorUtil.FormFriendInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int getSum(String uid) {
		int result = 0;
		try{
			result = (Integer)this.getSqlMapClientTemplate().queryForObject("FriendInfo.getSum", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteFriendInfo error"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
