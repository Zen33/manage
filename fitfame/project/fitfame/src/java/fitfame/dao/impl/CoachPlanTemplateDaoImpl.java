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
import fitfame.dao.ICoachPlanTemplateDao;
import fitfame.po.CoachPlanTemplate;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachPlanTemplateDaoImpl extends BaseDAO<CoachPlanTemplate>
		implements ICoachPlanTemplateDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#getCoachPlanTemplateList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CoachPlanTemplate> getCoachPlanTemplateList(String cid) {
		// TODO Auto-generated method stub
		List<CoachPlanTemplate> result = null;
		try{
			result = (List<CoachPlanTemplate>)this.getSqlMapClientTemplate().queryForList("CoachPlanTemplate.getCoachPlanTemplateList", cid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachPlanTemplateList error cid:"+String.valueOf(cid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#getCoachPlanTemplate(long)
	 */
	@Override
	public CoachPlanTemplate getCoachPlanTemplate(long pid) {
		// TODO Auto-generated method stub
		CoachPlanTemplate result = null; 
		try{
			result = (CoachPlanTemplate) this.getSqlMapClientTemplate().queryForObject(
					"CoachPlanTemplate.getCoachPlanTemplate", pid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getCoachPlanTemplate error cid:"+String.valueOf(pid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#insertCCoachPlanTemplate(fitfame.po.CoachPlanTemplate)
	 */
	@Override
	public int insertCoachPlanTemplate(CoachPlanTemplate plan) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("CoachPlanTemplate.insertCoachPlanTemplate", plan);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachPlanTemplateLog(plan));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#updateCoachPlanTemplate(fitfame.po.CoachPlanTemplate)
	 */
	@Override
	public int updateCoachPlanTemplate(CoachPlanTemplate plan) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("CoachPlanTemplate.updateCoachPlanTemplate", plan);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachPlanTemplateLog(plan));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int deleteCoachPlanTemplate(long pid) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("CoachPlanTemplate.deleteCoachPlanTemplate", pid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteCoachPlanTemplate error cid:"+String.valueOf(pid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
