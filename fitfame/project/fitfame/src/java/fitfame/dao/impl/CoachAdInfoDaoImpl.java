/**
 * 
 */
package fitfame.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.ICoachAdInfoDao;
import fitfame.po.CoachAdInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachAdInfoDaoImpl extends BaseDAO<CoachAdInfo> implements ICoachAdInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachAdInfoDao#getCoachAdInfoList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoachAdInfo> getCoachAdInfoList(String cid) {
		List<CoachAdInfo> result =null;
		try{
			result = (List<CoachAdInfo>)this.getSqlMapClientTemplate().queryForList("CoachAdInfo.getCoachAdInfoList", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachAdInfoList error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachAdInfoDao#insertCoachAdInfoList(fitfame.po.CoachAdInfo)
	 */
	@Override
	public int insertCoachAdInfo(CoachAdInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("CoachAdInfo.insertCoachAdInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "CoachAdInfo error url:"+info.getUrl());
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachAdInfoDao#deleteCoachAdInfoList(java.lang.String)
	 */
	@Override
	public int deleteCoachAdInfo(String url) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("CoachAdInfo.deleteCoachAdInfo", url);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "CoachAdInfo error url:"+url);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
