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
import fitfame.dao.IPersonalSubPlanDao;
import fitfame.po.PersonalSubPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class PersonalSubPlanDaoImpl extends BaseDAO<PersonalSubPlan> implements
		IPersonalSubPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#getPersonalSubPlanList(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalSubPlan> getPersonalSubPlanList(long ppid) {
		// TODO Auto-generated method stub
		List<PersonalSubPlan> result =null;
		try{
			result = (List<PersonalSubPlan>)this.getSqlMapClientTemplate().queryForList("PersonalSubPlan.getPersonalSubPlanList", ppid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonalSubPlanList error ppid:"+ppid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#insertPersonalSubPlan(fitfame.po.PersonalSubPlan)
	 */
	@Override
	public int insertPersonalSubPlan(PersonalSubPlan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("PersonalSubPlan.insertPersonalSubPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertPersonalSubPlan error"+SqlErrorUtil.FormPersonalSubPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#updatePersonalSubPlan(fitfame.po.PersonalSubPlan)
	 */
	@Override
	public int updatePersonalSubPlan(PersonalSubPlan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("PersonalSubPlan.updatePersonalSubPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updatePersonalSubPlan error"+SqlErrorUtil.FormPersonalSubPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#deletePersonalSubPlan(fitfame.po.PersonalSubPlan)
	 */
	@Override
	public int deletePersonalSubPlan(PersonalSubPlan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("PersonalSubPlan.deletePersonalSubPlan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deletePersonalSubPlan error"+SqlErrorUtil.FormPersonalSubPlanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public PersonalSubPlan getPersonalSubPlan(long id) {
		PersonalSubPlan result =null;
		try{
			result = (PersonalSubPlan)this.getSqlMapClientTemplate().queryForObject("PersonalSubPlan.getPersonalSubPlan", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonalSubPlan error id:"+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
