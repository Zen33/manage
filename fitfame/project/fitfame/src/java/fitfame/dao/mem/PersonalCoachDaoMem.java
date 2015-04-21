/**
 * 
 */
package fitfame.dao.mem;


import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.IPersonalCoachDao;
import fitfame.po.PersonalCoach;

/**
 * @author zhangshu
 *
 */
@Repository
public class PersonalCoachDaoMem implements IPersonalCoachDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#getPersonalCoach(java.lang.String)
	 */
	@Override
	public List<PersonalCoach> getPersonalCoachList(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#insertPersonalCoach(fitfame.po.PersonalCoach)
	 */
	@Override
	public int insertPersonalCoach(PersonalCoach info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#updatePersonalCoach(fitfame.po.PersonalCoach)
	 */
	@Override
	public int updatePersonalCoach(PersonalCoach info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IPersonalCoachDao#deletePersonalCoach(fitfame.po.PersonalCoach)
	 */
	@Override
	public int deletePersonalCoach(PersonalCoach info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PersonalCoach getPersonalCoach(String uid, long sid) {
		// TODO Auto-generated method stub
		return null;
	}

@Override
	public List<PersonalCoach> getPersonCoachByOnlineService(long sid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonalCoach> getPersonCoachByOfflineService(long sid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonalCoach> getPersonalCoachs(String cid) {
		// TODO Auto-generated method stub
		return null;
	}
}
