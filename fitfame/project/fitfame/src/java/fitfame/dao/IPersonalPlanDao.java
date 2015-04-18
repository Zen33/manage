/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.PersonalPlan;

/**
 * @author zhangshu
 *
 */
public interface IPersonalPlanDao {
	
	public List<PersonalPlan> getPersonalPlanList(String uid);
	
	public PersonalPlan getPersonalPlan(long id);

	public PersonalPlan getUndoPersonalPlan(String uid);
	
	public PersonalPlan getDonePersonalPlan(String uid, long edate);
	
	public long insertPersonalPlan(PersonalPlan info);
	
	public int updatePersonalPlan(PersonalPlan info);
	
	public int deletePersonalPlan(long id);
}
