/**
 * 
 */
package fitfame.dao.mem;

import org.springframework.stereotype.Repository;

import fitfame.dao.IStateInfoDao;
import fitfame.po.StateInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class StateInfoDaoMem implements IStateInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#getStateInfo(java.lang.String)
	 */
	@Override
	public StateInfo getStateInfo(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#insertStateInfo(fitfame.po.StateInfo)
	 */
	@Override
	public int insertStateInfo(StateInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#updateStateInfo(fitfame.po.StateInfo)
	 */
	@Override
	public int updateStateInfo(StateInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IStateInfoDao#deleteStateInfo(java.lang.String)
	 */
	@Override
	public int deleteStateInfo(String uid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
