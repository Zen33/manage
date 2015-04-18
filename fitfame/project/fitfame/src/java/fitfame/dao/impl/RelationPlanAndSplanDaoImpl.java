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
import fitfame.dao.IRelationPlanAndSplanDao;
import fitfame.po.RelationPlanAndSplan;

/**
 * @author zhangshu
 *
 */
@Repository
public class RelationPlanAndSplanDaoImpl extends BaseDAO<RelationPlanAndSplan> implements IRelationPlanAndSplanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationPlanAndSplanDao#insertRelationPlanAndSplan(fitfame.po.RelationPlanAndSplan)
	 */
	@Override
	public int insertRelationPlanAndSplan(RelationPlanAndSplan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("RelationPlanAndSplan.insertRelationPlanAndSplan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertRelationPlanAndSplan error"+SqlErrorUtil.FormRelationPlanAndSplanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationPlanAndSplanDao#updateRelationPlanAndSplan(fitfame.po.RelationPlanAndSplan)
	 */
	@Override
	public int updateRelationPlanAndSplan(RelationPlanAndSplan info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("RelationPlanAndSplan.updateRelationPlanAndSplan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateRelationPlanAndSplan error"+SqlErrorUtil.FormRelationPlanAndSplanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationPlanAndSplanDao#deleteRelationPlanAndSplan(long)
	 */
	@Override
	public int deleteRelationPlanAndSplan(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("RelationPlanAndSplan.deleteRelationPlanAndSplan", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteRelationPlanAndSplan error id:"+String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int getTopRankSplan(long pid) {
		int result = -1;
		try{
			result = (Integer) this.getSqlMapClientTemplate().queryForObject("RelationPlanAndSplan.getTopRankSplan", pid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getTopRankSplan error pid="+pid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public RelationPlanAndSplan getRelationPlanAndSplan(RelationPlanAndSplan info) {
		// TODO Auto-generated method stub
		RelationPlanAndSplan result = null;
		try{
			result = (RelationPlanAndSplan) this.getSqlMapClientTemplate().queryForObject("RelationPlanAndSplan.getRelationPlanAndSplan", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSplanId error"+SqlErrorUtil.FormRelationPlanAndSplanLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
