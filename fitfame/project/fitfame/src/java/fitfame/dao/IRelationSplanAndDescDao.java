/**
 * 
 */
package fitfame.dao;

import fitfame.po.RelationSplanAndDesc;

/**
 * @author zhangshu
 *
 */
public interface IRelationSplanAndDescDao {

	public int getTopRankDesc(long spid);
	
	public int insertRelationSplanAndDesc(RelationSplanAndDesc info);
	
	public int updateRelationSplanAndDesc(RelationSplanAndDesc info);
	
	public int deleteRelationSplanAndDesc(long id);
}
