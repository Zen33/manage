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
import fitfame.dao.ICoachPlanDao;
import fitfame.po.CoachPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachPlanDaoImpl extends BaseDAO<CoachPlan> implements
		ICoachPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#getCoachPlanList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoachPlan> getCoachPlanList(String cid) {
		// TODO Auto-generated method stub
		List<CoachPlan> result = null;
		try{
			result = (List<CoachPlan>)this.getSqlMapClientTemplate().queryForList("CoachPlan.getCoachPlanList", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachPlanList error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#getCoachPlan(long)
	 */
	@Override
	public CoachPlan getCoachPlan(long pid) {
		// TODO Auto-generated method stub
		CoachPlan result = null; 
		try{
			result = (CoachPlan) this.getSqlMapClientTemplate().queryForObject(
					"CoachPlan.getCoachPlan", pid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachPlan error cid:"+String.valueOf(pid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#insertCoachPlan(fitfame.po.CoachPlan)
	 */
	@Override
	public int insertCoachPlan(CoachPlan plan) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("CoachPlan.insertCoachPlan", plan);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachPlanLog(plan));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#updateCoachPlan(fitfame.po.CoachPlan)
	 */
	@Override
	public int updateCoachPlan(CoachPlan plan) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("CoachPlan.updateCoachPlan", plan);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachPlanLog(plan));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#deleteCoachPlan(long)
	 */
	@Override
	public int deleteCoachPlan(long pid) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("CoachPlan.updateCoachPlan", pid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteCoachPlan error cid:"+String.valueOf(pid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
