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
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.ICoachServiceDao;
import fitfame.po.CoachService;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachServiceDaoImpl extends BaseDAO<CoachService> implements
		ICoachServiceDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#getCoachServiceList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoachService> getCoachServiceList(String cid) {
		// TODO Auto-generated method stub
		List<CoachService> result =null;
		try{
			result = (List<CoachService>)this.getSqlMapClientTemplate().queryForList("CoachService.getCoachServiceList", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachServiceList error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}
	
	@Override
	public CoachService getCoachService(long sid) {
		// TODO Auto-generated method stub
		CoachService result =null;
		try{
			result = (CoachService)this.getSqlMapClientTemplate().queryForObject("CoachService.getCoachService", sid);
		} catch (DataAccessException e) {
			result = null;
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachService error cid:"+String.valueOf(sid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#insertCoachService(fitfame.po.CoachService)
	 */
	@Override
	public long insertCoachService(CoachService service) {
		// TODO Auto-generated method stub
		long result = 0;
		try{
			result = (Long)this.getSqlMapClientTemplate().insert("CoachService.insertCoachService", service);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachServiceLog(service));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#updateCoachService(fitfame.po.CoachService)
	 */
	@Override
	public int updateCoachService(CoachService service) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("CoachService.updateCoachService", service);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachServiceLog(service));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#deleteCoachService(long)
	 */
	@Override
	public int deleteCoachService(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("CoachService.deleteCoachService", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachServiceList error sid:"+String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
