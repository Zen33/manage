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
import fitfame.dao.ICoachInfoDao;
import fitfame.po.CoachInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachInfoDaoImpl extends BaseDAO<CoachInfo> implements
		ICoachInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#getCoachInfoList(fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CoachInfo> getCoachInfoList(PageInfo page) {
		// TODO Auto-generated method stub
		List<CoachInfo> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("null", "");
		try{
			result = pagedQueryInDB("getCoachInfoList", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachInfoList error");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#getCoachInfo(java.lang.String)
	 */
	@Override
	public CoachInfo getCoachInfo(String cid) {
		// TODO Auto-generated method stub
		CoachInfo info = null; 
		try{
			info = (CoachInfo) this.getSqlMapClientTemplate().queryForObject(
					"CoachInfo.getCoachInfo", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachInfo error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return info;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#insertCoachInfo(fitfame.po.CoachInfo)
	 */
	@Override
	public int insertCoachInfo(CoachInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("CoachInfo.insertCoachInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#updateCoachInfo(fitfame.po.CoachInfo)
	 */
	@Override
	public int updateCoachInfo(CoachInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("CoachInfo.updateCoachInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#deleteCoachInfo(java.lang.String)
	 */
	@Override
	public int deleteCoachInfo(String cid) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("CoachInfo.deleteCoachInfo", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,  "deleteCoachInfo error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
