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
import fitfame.dao.ISquareAdInfoDao;
import fitfame.po.SquareAdInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class SquareAdInfoDaoImpl extends BaseDAO<SquareAdInfo> implements ISquareAdInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ISquareAdInfo#getSquareAdInfoList()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SquareAdInfo> getSquareAdInfoList() {
		List<SquareAdInfo> result =null;
		try{
			result = (List<SquareAdInfo>)this.getSqlMapClientTemplate().queryForList("SquareAdInfo.getSquareAdInfoList");
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSquareAdInfoList error");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISquareAdInfoDao#insertSquareAdInfoList(fitfame.po.SquareAdInfo)
	 */
	@Override
	public int insertSquareAdInfo(SquareAdInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("SquareAdInfo.insertSquareAdInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "SquareAdInfo error url:"+info.getUrl());
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISquareAdInfoDao#deleteSquareAdInfoList(java.lang.String)
	 */
	@Override
	public int deleteSquareAdInfo(String url) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("SquareAdInfo.deleteSquareAdInfoList", url);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "SquareAdInfo error url:"+url);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
