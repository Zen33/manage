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
import fitfame.common.util.SqlErrorUtil;
import fitfame.dao.ISubPlanDescDao;
import fitfame.po.SubPlanDesc;

/**
 * @author zhangshu
 *
 */
@Repository
public class SubPlanDescDaoImpl extends BaseDAO<SubPlanDesc> implements
		ISubPlanDescDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDescDao#getSubPlanDesc(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SubPlanDesc> getSubPlanDesc(long sid) {
		// TODO Auto-generated method stub
		List<SubPlanDesc> result =null;
		try{
			result = (List<SubPlanDesc>)this.getSqlMapClientTemplate().queryForList("SubPlanDesc.getSubPlanDesc", sid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanDesc error uid:"+sid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDescDao#insertSubPlanDesc(fitfame.po.SubPlanDesc)
	 */
	@Override
	public int insertSubPlanDesc(SubPlanDesc info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().insert("SubPlanDesc.insertSubPlanDesc", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "insertSubPlanDesc error"+SqlErrorUtil.FormSubPlanDescLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDescDao#updateSubPlanDesc(fitfame.po.SubPlanDesc)
	 */
	@Override
	public int updateSubPlanDesc(SubPlanDesc info) {
		// TODO Auto-generated method stub
		int result = 0;
		try{
			this.getSqlMapClientTemplate().update("SubPlanDesc.updateSubPlanDesc", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "updateSubPlanDesc error"+SqlErrorUtil.FormSubPlanDescLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubPlanDesc> getCoachOwnPlanDesc(String uid) {
		// TODO Auto-generated method stub
		List<SubPlanDesc> result =null;
		try{
			result = (List<SubPlanDesc>)this.getSqlMapClientTemplate().queryForList("SubPlanDesc.getCoachOwnPlanDesc", uid);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanDesc error uid:"+uid);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubPlanDesc> getSharePlanDesc() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<SubPlanDesc> result =null;
		try{
			result = (List<SubPlanDesc>)this.getSqlMapClientTemplate().queryForList("SubPlanDesc.getCoachOwnPlanDesc");
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanDesc error ");
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public int deleteSubPlanDesc(SubPlanDesc info) {
		int result = 0;
		try{
			this.getSqlMapClientTemplate().delete("SubPlanDesc.deleteSubPlanDesc", info);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result = 1;
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "deleteSubPlanDesc error"+SqlErrorUtil.FormSubPlanDescLog(info));
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

	@Override
	public SubPlanDesc getSubPlanDescWithId(long id) {
		SubPlanDesc result =null;
		try{
			result = (SubPlanDesc)this.getSqlMapClientTemplate().queryForObject("SubPlanDesc.getSubPlanDescWithId", id);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			LogUtil.WriteLog(ExceptionIdUtil.IllegalSqlOperation, "getSubPlanDesc error uid:"+id);
			throw new BaseDaoException(ExceptionIdUtil.IllegalSqlOperation);
		}
		return result;
	}

}
