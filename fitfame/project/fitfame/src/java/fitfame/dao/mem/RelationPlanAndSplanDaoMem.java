/**
 * 
 */
package fitfame.dao.mem;

import org.springframework.stereotype.Repository;

import fitfame.dao.IRelationPlanAndSplanDao;
import fitfame.po.RelationPlanAndSplan;

/**
 * @author zhangshu
 *
 */
@Repository
public class RelationPlanAndSplanDaoMem implements IRelationPlanAndSplanDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationPlanAndSplanDao#insertRelationPlanAndSplan(fitfame.po.RelationPlanAndSplan)
	 */
	@Override
	public int insertRelationPlanAndSplan(RelationPlanAndSplan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationPlanAndSplanDao#updateRelationPlanAndSplan(fitfame.po.RelationPlanAndSplan)
	 */
	@Override
	public int updateRelationPlanAndSplan(RelationPlanAndSplan info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationPlanAndSplanDao#deleteRelationPlanAndSplan(long)
	 */
	@Override
	public int deleteRelationPlanAndSplan(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTopRankSplan(long pid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RelationPlanAndSplan getRelationPlanAndSplan(RelationPlanAndSplan info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationPlanAndSplan getRelationPlanAndSplanWithId(long rid) {
		// TODO Auto-generated method stub
		return null;
	}

}
