/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.PersonalSubPlan;

/**
 * @author zhangshu
 *
 */
public interface IPersonalSubPlanDao {

	public List<PersonalSubPlan> getPersonalSubPlanList(long ppid);
	
	public PersonalSubPlan getPersonalSubPlan(long id);
	
	public int insertPersonalSubPlan(PersonalSubPlan info);
	
	public int updatePersonalSubPlan(PersonalSubPlan info);
	
	public int deletePersonalSubPlan(PersonalSubPlan info);
}
