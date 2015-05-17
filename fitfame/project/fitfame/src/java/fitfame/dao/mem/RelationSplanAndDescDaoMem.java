/**
 * 
 */
package fitfame.dao.mem;

import org.springframework.stereotype.Repository;

import fitfame.dao.IRelationSplanAndDescDao;
import fitfame.po.RelationSplanAndDesc;

/**
 * @author zhangshu
 *
 */
@Repository
public class RelationSplanAndDescDaoMem implements IRelationSplanAndDescDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationSplanAndDescDao#insertRelationSplanAndDesc(fitfame.po.RelationSplanAndDesc)
	 */
	@Override
	public int insertRelationSplanAndDesc(RelationSplanAndDesc info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationSplanAndDescDao#updateRelationSplanAndDesc(long)
	 */
	@Override
	public int updateRelationSplanAndDesc(RelationSplanAndDesc info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IRelationSplanAndDescDao#deleteRelationSplanAndDesc(fitfame.po.RelationSplanAndDesc)
	 */
	@Override
	public int deleteRelationSplanAndDesc(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTopRankDesc(long spid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateRelationSplanAndDescRank(int rank, long spid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RelationSplanAndDesc getRelationSplanAndDesc(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationSplanAndDesc getRelationSplanAndDescByDid(long did) {
		// TODO Auto-generated method stub
		return null;
	}

}
