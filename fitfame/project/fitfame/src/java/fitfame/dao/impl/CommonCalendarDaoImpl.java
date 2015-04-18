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
import fitfame.dao.ICommonCalendarDao;
import fitfame.po.CommonCalendar;

/**
 * @author zhangshu
 *
 */
@Repository
public class CommonCalendarDaoImpl extends BaseDAO<CommonCalendar> implements
		ICommonCalendarDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICommonCalendarDao#getMonthCalendar(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonCalendar> getMonthCalendar(int month) {
		// TODO Auto-generated method stub
		List<CommonCalendar> res = null;
		try{
			res = (List<CommonCalendar>)this.getSqlMapClientTemplate().queryForList("CommonCalendar.getMonthCalendar", month);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getMonthCalendar error month:"+String.valueOf(month));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return res;
	}

}
