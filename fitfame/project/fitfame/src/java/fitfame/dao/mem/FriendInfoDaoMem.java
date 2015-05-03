/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.common.page.PageInfo;
import fitfame.dao.IFriendInfoDao;
import fitfame.po.FriendInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class FriendInfoDaoMem implements IFriendInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#getFriendInfoList(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<FriendInfo> getFriendInfoList(String uid, PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#getFriendInfo(fitfame.po.FriendInfo)
	 */
	@Override
	public FriendInfo getFriendInfo(FriendInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#insertFriendInfo(fitfame.po.FriendInfo)
	 */
	@Override
	public int insertFriendInfo(FriendInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.IFriendInfoDao#deleteFriendInfo(fitfame.po.FriendInfo)
	 */
	@Override
	public int deleteFriendInfo(FriendInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSum(String uid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
