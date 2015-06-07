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

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonCalendar> getMonthCalendar(int firstDay, int endDay) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("firstDay", firstDay);
		parameter.put("endDay", endDay);
		List<CommonCalendar> res = null;
		try{
			res = (List<CommonCalendar>)this.getSqlMapClientTemplate().queryForList("CommonCalendar.getMonthCalendar", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getMonthCalendar error month:"+firstDay);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return res;
	}

	@Override
	public CommonCalendar getCommonCalendarByDate(int date) {
		CommonCalendar result = null;
		try{
			result = (CommonCalendar)this.getSqlMapClientTemplate().queryForObject("CommonCalendar.getCommonCalendarByDate", date);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getMonthCalendar error month:"+date);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
