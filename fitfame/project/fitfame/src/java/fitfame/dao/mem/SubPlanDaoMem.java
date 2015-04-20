/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ISubPlanDao;
import fitfame.po.SubPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class SubPlanDaoMem implements ISubPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDao#getSubPlanList(long)
	 */
	@Override
	public List<SubPlan> getSubPlanList(long pid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDao#insertSubPlan(fitfame.po.SubPlan)
	 */
	@Override
	public int insertSubPlan(SubPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISubPlanDao#updateSubPlan(fitfame.po.SubPlan)
	 */
	@Override
	public int updateSubPlan(SubPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SubPlan> getOwnSubPlanList(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubPlan> getShareSubPlanList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubPlan getSubPlan(long sid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteSubPlan(long sid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
