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
import fitfame.dao.ITopicInfoDao;
import fitfame.po.TopicInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class TopicInfoDaoImpl extends BaseDAO<TopicInfo> implements
		ITopicInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#getNewTopicInfo(fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicInfo> getNewTopicInfo(PageInfo page) {
		// TODO Auto-generated method stub
		List<TopicInfo> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("null", null);
		try{
			result = pagedQueryInDB("getNewTopicInfo", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getTopicInfoList error");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#getTopicInfoList(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicInfo> getTopicInfoList(String uid, PageInfo page) {
		// TODO Auto-generated method stub
		List<TopicInfo> result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("uid", uid);
		try{
			result = pagedQueryInDB("getTopicInfoList", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getTopicInfoList error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#insertTopicInfo(fitfame.po.TopicInfo)
	 */
	@Override
	public int insertTopicInfo(TopicInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("TopicInfo.insertTopicInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertTopicInfo error"+SqlErrorUtil.FormTopicInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#updateTopicInfo(fitfame.po.TopicInfo)
	 */
	@Override
	public int updateTopicInfo(TopicInfo info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("TopicInfo.updateTopicInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateTopicInfo error"+SqlErrorUtil.FormTopicInfoLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#deleteTopicInfo(long)
	 */
	@Override
	public int deleteTopicInfo(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("TopicInfo.deleteTopicInfo", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteTopicInfo error id:"+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public TopicInfo getTopicInfo(long id) {
		TopicInfo result = null;
		try{
			result = (TopicInfo) this.getSqlMapClientTemplate().queryForObject("TopicInfo.getTopicInfo", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getTopicInfo error id:"+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
