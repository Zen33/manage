/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.common.page.PageInfo;
import fitfame.dao.ICoachInfoDao;
import fitfame.po.CoachInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachInfoDaoMem implements ICoachInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#getCoachInfoList(fitfame.common.page.PageInfo)
	 */
	@Override
	public List<CoachInfo> getCoachInfoList(PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#getCoachInfo(java.lang.String)
	 */
	@Override
	public CoachInfo getCoachInfo(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#insertCoachInfo(fitfame.po.CoachInfo)
	 */
	@Override
	public int insertCoachInfo(CoachInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#updateCoachInfo(fitfame.po.CoachInfo)
	 */
	@Override
	public int updateCoachInfo(CoachInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachInfoDao#deleteCoachInfo(java.lang.String)
	 */
	@Override
	public int deleteCoachInfo(String cid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
