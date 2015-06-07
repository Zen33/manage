package fitfame.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.IAdminInfoDao;
import fitfame.po.AdminInfo;

@Repository
public class AdminInfoDaoImpl extends BaseDAO<AdminInfo> implements IAdminInfoDao{

	@Override
	public AdminInfo getAdminInfo(AdminInfo admin) {
		AdminInfo info = null;
		try{
			info = (AdminInfo) this.getSqlMapClientTemplate().queryForObject(
					"AdminInfo.getAdminInfo", admin);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, admin.getName());
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return info;
	}

	@Override
	public int insertAdminInfo(AdminInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("AdminInfo.insertAdminInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, info.getName());
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int updateAdminInfo(AdminInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("AdminInfo.updateAdminInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, info.getName());
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int deleteAdminInfo(AdminInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("AdminInfo.deleteAdminInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, info.getName());
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
