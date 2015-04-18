/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ICoachPlanDao;
import fitfame.po.CoachPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachPlanDaoMem implements ICoachPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#getCoachPlanList(java.lang.String)
	 */
	@Override
	public List<CoachPlan> getCoachPlanList(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#getCoachPlan(long)
	 */
	@Override
	public CoachPlan getCoachPlan(long pid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#insertCoachPlan(fitfame.po.CoachPlan)
	 */
	@Override
	public int insertCoachPlan(CoachPlan plan) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#updateCoachPlan(fitfame.po.CoachPlan)
	 */
	@Override
	public int updateCoachPlan(CoachPlan plan) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanDao#deleteCoachPlan(long)
	 */
	@Override
	public int deleteCoachPlan(long pid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
