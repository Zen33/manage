package fitfame.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.exception.BaseDaoException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.IVerifyInfoDao;
import fitfame.po.VerifyInfo;

@Repository
public class VerifyInfoDaoImpl extends BaseDAO<VerifyInfo> implements IVerifyInfoDao{

	@Override
	public VerifyInfo getVerifyInfo(String phone) {
		VerifyInfo result = null;
		try{
			result = (VerifyInfo) this.getSqlMapClientTemplate().queryForObject("VerifyInfo.getVerifyInfo", phone);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, phone);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int insertVerifyInfo(VerifyInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("VerifyInfo.insertVerifyInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormVerifyInfo(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int updateVerifyInfo(VerifyInfo info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("VerifyInfo.updateVerifyInfo", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, SqlErrorUtil.FormVerifyInfo(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int deleteVerifyInfo(String phone) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("VerifyInfo.deleteVerifyInfo", phone);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, phone);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int updateVerifyAll(int verifyCount, int pwCount) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("pwCount", pwCount);
		parameter.put("verifyCount", verifyCount);
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("VerifyInfo.updateVerifyAll", parameter);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateVerifyAll" + verifyCount + ";" + pwCount);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
