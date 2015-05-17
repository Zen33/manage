/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CoachService;

/**
 * @author zhangshu
 *
 */
public interface ICoachServiceDao {

	public List<CoachService> getCoachServiceList(String cid);
	
	public CoachService getCoachService(long sid);
	
	public long insertCoachService(CoachService service);
	
	public int updateCoachService(CoachService service);
	
	public int deleteCoachService(long sid);
}
