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
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IPersonalPlanDao;
import fitfame.po.PersonalPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class PersonalPlanDaoImpl extends BaseDAO<PersonalPlan> implements
		IPersonalPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#getPersonalPlanList(java.lang.String)
	 */
	@Override
	public PersonalPlan getUndoPersonalPlan(String uid) {
		// TODO Auto-generated method stub
		PersonalPlan result =null;
		try{
			result =(PersonalPlan) this.getSqlMapClientTemplate().queryForObject("PersonalPlan.getUndoPersonalPlan", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getUndoPersonalPlan error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#insertPersonalPlan(fitfame.po.PersonalPlan)
	 */
	@Override
	public long insertPersonalPlan(PersonalPlan info) {
		// TODO Auto-generated method stub
		long result = 0;
		try{
			result = (long)this.getSqlMapClientTemplate().insert("PersonalPlan.insertPersonalPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertPersonalPlan error"+SqlErrorUtil.FormPersonalPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#updatePersonalPlan(fitfame.po.PersonalPlan)
	 */
	@Override
	public int updatePersonalPlan(PersonalPlan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("PersonalPlan.updatePersonalPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updatePersonalPlan error"+SqlErrorUtil.FormPersonalPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#deletePersonalPlan(long)
	 */
	@Override
	public int deletePersonalPlan(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("PersonalPlan.deletePersonalPlan", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deletePersonalPlan error id:"+String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public PersonalPlan getDonePersonalPlan(String uid, long edate) {
		// TODO Auto-generated method stub
		PersonalPlan result =null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("uid", uid);
		parameter.put("edate", edate);
		try{
			result = (PersonalPlan)this.getSqlMapClientTemplate().queryForObject("PersonalPlan.getDonePersonalPlan", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getDonePersonalPlan error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalPlan> getPersonalPlanList(String uid) {
		// TODO Auto-generated method stub
		List<PersonalPlan> result =null;
		try{
			result = (List<PersonalPlan>)this.getSqlMapClientTemplate().queryForList("PersonalPlan.getPersonalPlanList", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonalPlanList error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public PersonalPlan getPersonalPlan(long id) {
		PersonalPlan result =null;
		try{
			result =(PersonalPlan) this.getSqlMapClientTemplate().queryForObject("PersonalPlan.getPersonalPlan", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonalPlan error id:"+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
