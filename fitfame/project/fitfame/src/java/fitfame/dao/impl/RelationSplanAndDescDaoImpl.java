/**
 * 
 */
package fitfame.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IRelationSplanAndDescDao;
import fitfame.po.RelationSplanAndDesc;

/**
 * @author zhangshu
 *
 */
@Repository
public class RelationSplanAndDescDaoImpl extends BaseDAO<RelationSplanAndDesc> implements IRelationSplanAndDescDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationSplanAndDescDao#insertRelationSplanAndDesc(fitfame.po.RelationSplanAndDesc)
	 */
	@Override
	public int insertRelationSplanAndDesc(RelationSplanAndDesc info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("RelationSplanAndDesc.insertRelationSplanAndDesc", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertRelationSplanAndDesc error"+SqlErrorUtil.FormRelationSplanAndDescLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationSplanAndDescDao#updateRelationSplanAndDesc(long)
	 */
	@Override
	public int updateRelationSplanAndDesc(RelationSplanAndDesc info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("RelationSplanAndDesc.updateRelationSplanAndDesc", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateRelationSplanAndDesc error"+SqlErrorUtil.FormRelationSplanAndDescLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationSplanAndDescDao#deleteRelationSplanAndDesc(fitfame.po.RelationSplanAndDesc)
	 */
	@Override
	public int deleteRelationSplanAndDesc(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("RelationSplanAndDesc.deleteRelationSplanAndDesc", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteRelationSplanAndDesc error id:"+String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int getTopRankDesc(long spid) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			result = (int)this.getSqlMapClientTemplate().queryForObject("RelationSplanAndDesc.getTopRankDesc", spid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "RelationSplanAndDesc error spid="+spid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int updateRelationSplanAndDescRank(int rank, long spid) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("rank", rank);
		parameter.put("spid", spid);
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("RelationSplanAndDesc.updateRelationSplanAndDescRank", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "RelationSplanAndDesc error spid="+spid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public RelationSplanAndDesc getRelationSplanAndDesc(long id) {
		RelationSplanAndDesc result = null;
		try{
			result = (RelationSplanAndDesc)this.getSqlMapClientTemplate().queryForObject("RelationSplanAndDesc.getRelationSplanAndDesc", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "RelationSplanAndDesc error spid="+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
