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
import fitfame.dao.ITopicReplyDao;
import fitfame.po.TopicReply;

/**
 * @author zhangshu
 *
 */
@Repository
public class TopicReplyDaoImpl extends BaseDAO<TopicReply> implements
		ITopicReplyDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#getTopicReplyList(long, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicReply> getTopicReplyList(long tid, PageInfo page) {
		List<TopicReply> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("totid", tid);
		try{
			result = pagedQueryInDB("getTopicReplyList", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getTopicReplyList error tid:"+tid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#getUnreadTopicReply(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicReply> getUnreadTopicReply(String uid, PageInfo page) {
		List<TopicReply> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("touid", uid);
		try{
			result = pagedQueryInDB("getUnreadTopicReply", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getUnreadTopicReply error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#insertTopicReply(fitfame.po.TopicReply)
	 */
	@Override
	public int insertTopicReply(TopicReply info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("TopicReply.insertTopicReply", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertTopicReply error"+SqlErrorUtil.FormTopicReplyLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#updateTopicReply(fitfame.po.TopicReply)
	 */
	@Override
	public int updateTopicReply(TopicReply info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("TopicReply.updateTopicReply", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateTopicReply error"+SqlErrorUtil.FormTopicReplyLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#deleteTopicReply(long)
	 */
	@Override
	public int deleteTopicReply(long id) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("TopicReply.deleteTopicReply", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteTopicReply error id:"+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#deleteAllTopicReply(long)
	 */
	@Override
	public int deleteAllTopicReply(long tid) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("TopicReply.deleteAllTopicReply", tid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteAllTopicReply error tid:"+tid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
