/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ICoachServiceDao;
import fitfame.po.CoachService;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachServiceDaoMem implements ICoachServiceDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#getCoachServiceList(java.lang.String)
	 */
	@Override
	public List<CoachService> getCoachServiceList(String cid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public CoachService getCoachService(long sid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#insertCoachService(fitfame.po.CoachService)
	 */
	@Override
	public int insertCoachService(CoachService service) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#updateCoachService(fitfame.po.CoachService)
	 */
	@Override
	public int updateCoachService(CoachService service) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachServiceDao#deleteCoachService(long)
	 */
	@Override
	public int deleteCoachService(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
