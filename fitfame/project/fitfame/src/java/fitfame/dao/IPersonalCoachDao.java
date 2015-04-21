/**
 * 
 */
package fitfame.dao;


import java.util.List;

import fitfame.po.PersonalCoach;

/**
 * @author zhangshu
 *
 */
public interface IPersonalCoachDao {
	
	public PersonalCoach getPersonalCoach(String uid, long sid);

	public List<PersonalCoach> getPersonalCoachList(String uid);
	
	public List<PersonalCoach> getPersonCoachByOnlineService(long sid);
	
	public List<PersonalCoach> getPersonCoachByOfflineService(long sid);
	
	public List<PersonalCoach> getPersonalCoachs(String cid);
	
	public int insertPersonalCoach(PersonalCoach info);
	
	public int updatePersonalCoach(PersonalCoach info);
	
	public int deletePersonalCoach(PersonalCoach info);
}
