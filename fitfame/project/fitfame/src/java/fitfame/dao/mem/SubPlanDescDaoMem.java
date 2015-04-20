/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ISubPlanDescDao;
import fitfame.po.SubPlanDesc;

/**
 * @author zhangshu
 *
 */
@Repository
public class SubPlanDescDaoMem implements ISubPlanDescDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDescDao#getSubPlanDesc(long)
	 */
	@Override
	public List<SubPlanDesc> getSubPlanDesc(long sid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDescDao#insertSubPlanDesc(fitfame.po.SubPlanDesc)
	 */
	@Override
	public int insertSubPlanDesc(SubPlanDesc info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDescDao#updateSubPlanDesc(fitfame.po.SubPlanDesc)
	 */
	@Override
	public int updateSubPlanDesc(SubPlanDesc info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SubPlanDesc> getCoachOwnPlanDesc(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubPlanDesc> getSharePlanDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteSubPlanDesc(SubPlanDesc info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SubPlanDesc getSubPlanDescWithId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
