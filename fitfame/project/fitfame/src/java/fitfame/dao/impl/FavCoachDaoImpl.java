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
import fitfame.dao.IFavCoachDao;
import fitfame.po.FavCoach;

/**
 * @author zhangshu
 *
 */
@Repository
public class FavCoachDaoImpl extends BaseDAO<FavCoach> implements IFavCoachDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IFavCoachDao#getFavCoach(fitfame.po.FavCoach)
	 */
	@Override
	public FavCoach getFavCoach(FavCoach info) {
		// TODO Auto-generated method stub
		FavCoach result = null;
		try{
			result = (FavCoach) this.getSqlMapClientTemplate().queryForObject(
					"FavCoach.getFavCoach", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getFavCoach error cid:"+SqlErrorUtil.FormFavCoachLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFavCoachDao#insertFavCoach(fitfame.po.FavCoach)
	 */
	@Override
	public int insertFavCoach(FavCoach info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("FavCoach.insertFavCoach", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertFavCoach error cid:"+SqlErrorUtil.FormFavCoachLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
