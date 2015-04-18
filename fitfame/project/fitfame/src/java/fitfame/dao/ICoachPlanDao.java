/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CoachPlan;

/**
 * @author zhangshu
 *
 */
public interface ICoachPlanDao {

	public List<CoachPlan> getCoachPlanList(String cid);
	
	public CoachPlan getCoachPlan(long pid);
	
	public int insertCoachPlan(CoachPlan plan);
	
	public int updateCoachPlan(CoachPlan plan);
	
	public int deleteCoachPlan(long pid);
}
