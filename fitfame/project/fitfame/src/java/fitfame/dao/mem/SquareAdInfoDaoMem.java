/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ISquareAdInfoDao;
import fitfame.po.SquareAdInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class SquareAdInfoDaoMem implements ISquareAdInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ISquareAdInfo#getSquareAdInfoList()
	 */
	@Override
	public List<SquareAdInfo> getSquareAdInfoList() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISquareAdInfo#insertSquareAdInfo(fitfame.po.SquareAdInfo)
	 */
	@Override
	public int insertSquareAdInfo(SquareAdInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ISquareAdInfo#deleteSquareAdInfo(long)
	 */
	@Override
	public int deleteSquareAdInfo(String url) {
		// TODO Auto-generated method stub
		return 0;
	}

}
