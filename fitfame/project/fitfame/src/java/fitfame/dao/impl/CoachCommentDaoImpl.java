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
import fitfame.common.page.PageInfo;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.ICoachCommentDao;
import fitfame.po.CoachComment;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachCommentDaoImpl extends BaseDAO<CoachComment> implements
		ICoachCommentDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#getCoachCommentByCid(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CoachComment> getCoachCommentByCid(String cid, PageInfo page) {
		// TODO Auto-generated method stub
		List<CoachComment> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		try{
			result = pagedQueryInDB("getCoachCommentByCid", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, cid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#getUnreadCoachCommentByCid(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CoachComment> getUnreadCoachCommentByCid(String cid,
			PageInfo page) {
		// TODO Auto-generated method stub
		List<CoachComment> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		try{
			result = pagedQueryInDB("getUnreadCoachCommentByCid", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, cid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#insertCoachComment(fitfame.po.CoachComment)
	 */
	@Override
	public int insertCoachComment(CoachComment comment) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("CoachComment.insertCoachComment", comment);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachCommentLog(comment));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#updateCoachComment(fitfame.po.CoachComment)
	 */
	@Override
	public int updateCoachComment(CoachComment comment) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("CoachComment.updateCoachComment", comment);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormCoachCommentLog(comment));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachCommentDao#deleteCoachComment(long)
	 */
	@Override
	public int deleteCoachComment(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("CoachComment.deleteCoachComment", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
