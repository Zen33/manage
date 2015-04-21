/**
 * 
 */
package fitfame.dao;

import fitfame.po.RelationPlanAndSplan;

/**
 * @author zhangshu
 *
 */
public interface IRelationPlanAndSplanDao {

	public int getTopRankSplan(long pid);
	
	public RelationPlanAndSplan getRelationPlanAndSplan(RelationPlanAndSplan info);
	
	public int insertRelationPlanAndSplan(RelationPlanAndSplan info);
	
	public int updateRelationPlanAndSplan(RelationPlanAndSplan info);
	
	public int deleteRelationPlanAndSplan(long id);
	
	public RelationPlanAndSplan getRelationPlanAndSplanWithId(long rid);
}
