/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.IPersonalPlanDao;
import fitfame.po.PersonalPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class PersonalPlanDaoMem implements IPersonalPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#getPersonalPlanList(java.lang.String)
	 */
	@Override
	public PersonalPlan getUndoPersonalPlan(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#insertPersonalPlan(fitfame.po.PersonalPlan)
	 */
	@Override
	public long insertPersonalPlan(PersonalPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#updatePersonalPlan(fitfame.po.PersonalPlan)
	 */
	@Override
	public int updatePersonalPlan(PersonalPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalPlanDao#deletePersonalPlan(long)
	 */
	@Override
	public int deletePersonalPlan(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersonalPlan getDonePersonalPlan(String uid, long edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonalPlan> getPersonalPlanList(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonalPlan getPersonalPlan(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
