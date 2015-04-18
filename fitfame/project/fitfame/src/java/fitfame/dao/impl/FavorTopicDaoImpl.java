package fitfame.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IFavorTopicDao;
import fitfame.po.FavorTopic;

@Repository
public class FavorTopicDaoImpl extends BaseDAO<FavorTopic> implements IFavorTopicDao{

	@Override
	public FavorTopic getFavorTopic(FavorTopic info) {
		FavorTopic result = null;
		try{
			result = (FavorTopic) this.getSqlMapClientTemplate().queryForObject(
					"FavorTopic.getFavorTopic", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getFavorTopic error cid:"+SqlErrorUtil.FormFavTopicLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int insertFavorTopic(FavorTopic info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("FavorTopic.insertFavorTopic", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertFavorTopic error cid:"+SqlErrorUtil.FormFavTopicLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
