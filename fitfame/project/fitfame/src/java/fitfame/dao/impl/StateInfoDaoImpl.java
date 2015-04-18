/**
 * 
 */
package fitfame.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IStateInfoDao;
import fitfame.po.StateInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class StateInfoDaoImpl extends BaseDAO<StateInfo> implements
		IStateInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#getStateInfo(java.lang.String)
	 */
	@Override
	public StateInfo getStateInfo(String uid) {
		// TODO Auto-generated method stub
		StateInfo info = null; 
		try{
			info = (StateInfo) this.getSqlMapClientTemplate().queryForObject(
					"StateInfo.getStateInfo", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getStateInfo error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return info;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#insertStateInfo(fitfame.po.StateInfo)
	 */
	@Override
	public int insertStateInfo(StateInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("StateInfo.insertStateInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormStateInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#updateStateInfo(fitfame.po.StateInfo)
	 */
	@Override
	public int updateStateInfo(StateInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("StateInfo.updateStateInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormStateInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#deleteStateInfo(java.lang.String)
	 */
	@Override
	public int deleteStateInfo(String uid) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("StateInfo.deleteStateInfo", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteStateInfo error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
