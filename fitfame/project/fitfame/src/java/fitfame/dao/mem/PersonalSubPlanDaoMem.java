/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.IPersonalSubPlanDao;
import fitfame.po.PersonalSubPlan;

/**
 * @author zhangshu
 *
 */
@Repository
public class PersonalSubPlanDaoMem implements IPersonalSubPlanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#getPersonalSubPlanList(long)
	 */
	@Override
	public List<PersonalSubPlan> getPersonalSubPlanList(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#insertPersonalSubPlan(fitfame.po.PersonalSubPlan)
	 */
	@Override
	public int insertPersonalSubPlan(PersonalSubPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#updatePersonalSubPlan(fitfame.po.PersonalSubPlan)
	 */
	@Override
	public int updatePersonalSubPlan(PersonalSubPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalSubPlanDao#deletePersonalSubPlan(fitfame.po.PersonalSubPlan)
	 */
	@Override
	public int deletePersonalSubPlan(PersonalSubPlan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersonalSubPlan getPersonalSubPlan(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
