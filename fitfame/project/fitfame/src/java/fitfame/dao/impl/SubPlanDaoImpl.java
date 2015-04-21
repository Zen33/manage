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
import fitfame.dao.ISubPlanDao;
import fitfame.po.SubPlan;
import fitfame.po.SubPlanWithId;

/**
 * @author zhangshu
 *
 */
@Repository
public class SubPlanDaoImpl extends BaseDAO<SubPlan> implements ISubPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDao#getSubPlanList(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SubPlanWithId> getSubPlanList(long pid) {
		// TODO Auto-generated method stub
		List<SubPlanWithId> result =null;
		try{
			result = (List<SubPlanWithId>)this.getSqlMapClientTemplate().queryForList("SubPlan.getSubPlanList", pid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanList error pid:"+pid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDao#insertSubPlan(fitfame.po.SubPlan)
	 */
	@Override
	public int insertSubPlan(SubPlan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("SubPlan.insertSubPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertSubPlan error"+SqlErrorUtil.FormSubPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDao#updateSubPlan(fitfame.po.SubPlan)
	 */
	@Override
	public int updateSubPlan(SubPlan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("SubPlan.updateSubPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateSubPlan error"+SqlErrorUtil.FormSubPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubPlan> getOwnSubPlanList(String cid) {
		// TODO Auto-generated method stub
		List<SubPlan> result =null;
		try{
			result = (List<SubPlan>)this.getSqlMapClientTemplate().queryForList("SubPlan.getOwnSubPlanList", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanList error cid:"+cid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubPlan> getShareSubPlanList() {
		// TODO Auto-generated method stub
		List<SubPlan> result =null;
		try{
			result = (List<SubPlan>)this.getSqlMapClientTemplate().queryForList("SubPlan.getOwnSubPlanList");
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanList error ");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public SubPlan getSubPlan(long sid) {
		SubPlan result =null;
		try{
			result = (SubPlan)this.getSqlMapClientTemplate().queryForObject("SubPlan.getSubPlan", sid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlan error pid:"+sid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int deleteSubPlan(long sid) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("SubPlan.deleteSubPlan", sid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateSubPlan error"+sid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
