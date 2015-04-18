/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.SubPlan;

/**
 * @author zhangshu
 *
 */
public interface ISubPlanDao {

	public List<SubPlan> getSubPlanList(long pid);
	
	public List<SubPlan> getOwnSubPlanList(String cid);
	
	public List<SubPlan> getShareSubPlanList();
	
	public int insertSubPlan(SubPlan info);
	
	public int updateSubPlan(SubPlan info);
}
