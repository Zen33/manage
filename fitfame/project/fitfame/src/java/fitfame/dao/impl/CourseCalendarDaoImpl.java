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
import fitfame.dao.ICourseCalendarDao;
import fitfame.po.CourseCalendar;

/**
 * @author zhangshu
 * 
 */
@Repository
public class CourseCalendarDaoImpl extends BaseDAO<CourseCalendar> implements
		ICourseCalendarDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fitfame.dao.ICourseCalendarDao#getMonthCourseCalendar(java.lang.String,
	 * int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseCalendar> getMonthCourseCalendar(String cid, int month) {
		// TODO Auto-generated method stub
		List<CourseCalendar> res = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		parameter.put("cdate", month * 10000);
		try {
			res = (List<CourseCalendar>) this.getSqlMapClientTemplate()
					.queryForList("CourseCalendar.getMonthCourseCalendar",
							parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getMonthCourseCalendar error cid:" + cid + " month:"
							+ String.valueOf(month));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fitfame.dao.ICourseCalendarDao#getUndoCourseCalendar(java.lang.String,
	 * long, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CourseCalendar> getUndoCourseCalendar(String uid, long date,
			PageInfo page) {
		// TODO Auto-generated method stub
		List<CourseCalendar> res = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("uid", uid);
		parameter.put("date", date);
		try {
			res = pagedQueryInDB("getUndoCourseCalendar", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getUndoCourseCalendar error");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fitfame.dao.ICourseCalendarDao#getDoneCourseCalendar(java.lang.String,
	 * long, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CourseCalendar> getDoneCourseCalendar(String uid, long date,
			PageInfo page) {
		// TODO Auto-generated method stub
		List<CourseCalendar> res = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("uid", uid);
		parameter.put("date", date);
		try {
			res = pagedQueryInDB("getDoneCourseCalendar", parameter, page);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getDoneCourseCalendar error");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fitfame.dao.ICourseCalendarDao#updateCourseCalendar(fitfame.po.CourseCalendar
	 * )
	 */
	@Override
	public int updateCourseCalendar(CourseCalendar info) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			this.getSqlMapClientTemplate().update(
					"CourseCalendar.updateCourseCalendar", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					SqlErrorUtil.FormCourseCalendarLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fitfame.dao.ICourseCalendarDao#insertCourseCalendar(fitfame.po.CourseCalendar
	 * )
	 */
	@Override
	public int insertCourseCalendar(CourseCalendar info) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			this.getSqlMapClientTemplate().insert(
					"CourseCalendar.insertCourseCalendar", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					SqlErrorUtil.FormCourseCalendarLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fitfame.dao.ICourseCalendarDao#deleteCourseCalendar(long)
	 */
	@Override
	public int deleteCourseCalendar(long id) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			this.getSqlMapClientTemplate().delete(
					"CourseCalendar.deleteCourseCalendar", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"deleteCourseCalendar error id:" + String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public CourseCalendar getCourseCalendar(long id) {
		// TODO Auto-generated method stub
		CourseCalendar result = null;
		try {
			result = (CourseCalendar) this.getSqlMapClientTemplate()
					.queryForObject("CourseCalendar.getCourseCalendar", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getCourseCalendar error cid:" + String.valueOf(id));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public CourseCalendar getPreviousCourseCalendar(String cid, long date) {
		// TODO Auto-generated method stub
		CourseCalendar result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		parameter.put("date", date);
		try {
			result = (CourseCalendar) this.getSqlMapClientTemplate()
					.queryForObject("CourseCalendar.getPreviousCourseCalendar",
							parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getPreviousCourseCalendar error cid:" + cid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public CourseCalendar getNextCourseCalendar(String cid, long date) {
		// TODO Auto-generated method stub
		CourseCalendar result = null;
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		parameter.put("date", date);
		try {
			result = (CourseCalendar) this.getSqlMapClientTemplate()
					.queryForObject("CourseCalendar.getPreviousCourseCalendar",
							parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getNextCourseCalendar error cid:" + cid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int getCourseNum(long tid, String cid) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		parameter.put("tid", tid);
		int result = 0;
		try {
			result = (Integer) this.getSqlMapClientTemplate().queryForObject(
					"CourseCalendar.getCourseNum", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"deleteCourseCalendar error id:" + String.valueOf(tid));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CourseCalendar> getCourseCalendars(long tid, String cid) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("cid", cid);
		parameter.put("tid", tid);

		List<CourseCalendar> res = null;
		try {
			res = (List<CourseCalendar>) this.getSqlMapClientTemplate()
					.queryForList("CourseCalendar.getCourseCalendars",
							parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation,
					"getDoneCourseCalendar error");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return res;
	}

}
