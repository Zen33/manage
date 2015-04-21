/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.SubPlan;
import fitfame.po.SubPlanWithId;

/**
 * @author zhangshu
 *
 */
public interface ISubPlanDao {

	public List<SubPlanWithId> getSubPlanList(long pid);
	
	public List<SubPlan> getOwnSubPlanList(String cid);
	
	public List<SubPlan> getShareSubPlanList();
	
	public SubPlan getSubPlan(long sid);
	
	public int deleteSubPlan(long sid);
	
	public int insertSubPlan(SubPlan info);
	
	public int updateSubPlan(SubPlan info);
}
