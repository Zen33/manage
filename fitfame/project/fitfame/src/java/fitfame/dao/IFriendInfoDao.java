/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.common.page.PageInfo;
import fitfame.po.FriendInfo;

/**
 * @author zhangshu
 *
 */
public interface IFriendInfoDao {

	public List<FriendInfo> getFriendInfoList(String uid, PageInfo page);
	
	public FriendInfo getFriendInfo(FriendInfo info);
	
	public int insertFriendInfo(FriendInfo info);
	
	public int deleteFriendInfo(FriendInfo info);
	
	public int getSum(String uid);
}
