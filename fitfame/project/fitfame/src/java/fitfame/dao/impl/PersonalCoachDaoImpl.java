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
import fitfame.dao.IPersonalCoachDao;
import fitfame.po.PersonalCoach;

/**
 * @author zhangshu
 *
 */
@Repository
public class PersonalCoachDaoImpl extends BaseDAO<PersonalCoach> implements
		IPersonalCoachDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#getPersonalCoach(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalCoach> getPersonalCoachList(String uid) {
		// TODO Auto-generated method stub
		List<PersonalCoach> result =null;
		try{
			result = (List<PersonalCoach>)this.getSqlMapClientTemplate().queryForList("PersonalCoach.getPersonalCoachList", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonalCoachList error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#insertPersonalCoach(fitfame.po.PersonalCoach)
	 */
	@Override
	public int insertPersonalCoach(PersonalCoach info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("PersonalCoach.insertPersonalCoach", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertPersonalCoach error"+SqlErrorUtil.FormPersonalCoachLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#updatePersonalCoach(fitfame.po.PersonalCoach)
	 */
	@Override
	public int updatePersonalCoach(PersonalCoach info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("PersonalCoach.updatePersonalCoach", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updatePersonalCoach error"+SqlErrorUtil.FormPersonalCoachLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#deletePersonalCoach(fitfame.po.PersonalCoach)
	 */
	@Override
	public int deletePersonalCoach(PersonalCoach info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("PersonalCoach.deletePersonalCoach", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deletePersonalCoach error"+SqlErrorUtil.FormPersonalCoachLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public PersonalCoach getPersonalCoach(String uid, long sid) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("uid", uid);
		parameter.put("sid", sid);
		PersonalCoach result = null;
		try{
			result = (PersonalCoach)this.getSqlMapClientTemplate().queryForObject("PersonalCoach.getPersonalCoach", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonalCoach error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}
	
		@SuppressWarnings("unchecked")
	@Override
	public List<PersonalCoach> getPersonCoachByOnlineService(long sid) {
		// TODO Auto-generated method stub
		List<PersonalCoach> result =null;
		try{
			result = (List<PersonalCoach>)this.getSqlMapClientTemplate().queryForList("PersonalCoach.getPersonCoachByOnlineService", sid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonCoachByOnlineService error sid:"+String.valueOf(sid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PersonalCoach> getPersonCoachByOfflineService(long sid) {
		// TODO Auto-generated method stub
		List<PersonalCoach> result =null;
		try{
			result = (List<PersonalCoach>)this.getSqlMapClientTemplate().queryForList("PersonalCoach.getPersonCoachByOfflineService", sid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getPersonCoachByOfflineService error sid:"+String.valueOf(sid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
