/**
 * 
 */
package fitfame.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fitfame.common.exception.BaseServiceException;
import fitfame.common.page.PageInfo;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.HanziUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.IFriendInfoDao;
import fitfame.dao.IUserInfoDao;
import fitfame.po.FriendInfo;
import fitfame.po.UserInfo;

/**
 * @author zhangshu
 *
 */
@Service
public class FriendService {
	@Autowired(required=true)
	@Qualifier("friendInfoDaoImpl")
	private IFriendInfoDao friendInfoDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;
	
	public JSONObject AddFriend(String uid1, String uid2){
		JSONObject json = new JSONObject();
		
		UserInfo user = userInfoDaoImpl.getUserInfoByUid(uid2);
		if(user == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.UserNotExsits, uid2);
			throw new BaseServiceException(ExceptionIdUtil.UserNotExsits, uid2);
		}
		
		FriendInfo info = new FriendInfo();
		info.setUid1(uid1);
		info.setUid2(uid2);
		if(friendInfoDaoImpl.getFriendInfo(info) == null)
		{
			friendInfoDaoImpl.insertFriendInfo(info);
		}
		json = UserBasicInfoWithoutPlan(user);
		return json;
	}
	
	private JSONObject UserBasicInfoWithoutPlan(UserInfo user) {
		JSONObject json = new JSONObject();
		JSONObject uJson = new JSONObject();
		uJson.accumulate("sex", user.getSex());
		uJson.accumulate("username", user.getUsername());
		uJson.accumulate("icon", user.getIcon());
		uJson.accumulate("birthday", user.getBrithday());
		uJson.accumulate("height", user.getHeight());
		uJson.accumulate("weight", user.getWeight());
		uJson.accumulate("uid", user.getUid());
		uJson.accumulate("firstLetter", HanziUtil.getFirstLetterOfName(user.getUsername()));
		json.accumulate("user_info", uJson);
		return json;
	}
	
	public void RemoveFriend(String uid1, String uid2){
		if (uid1.compareTo(uid2)>0){
			String temp = uid1;
			uid1 = uid2;
			uid2 = temp;
		}
		
		FriendInfo info = new FriendInfo();
		info.setUid1(uid1);
		info.setUid2(uid2);
		friendInfoDaoImpl.deleteFriendInfo(info);
	}
	
	public JSONObject queryMyFriend(String uid, PageInfo page){
		JSONObject json = new JSONObject();
		List<FriendInfo> friends= friendInfoDaoImpl.getFriendInfoList(uid, page);
		List<UserInfo> info = new ArrayList<UserInfo>();
		if(friends != null){
			for(int i = 0; i < friends.size(); i++)  
	        { 
				FriendInfo temp = friends.get(i);
				UserInfo user = new UserInfo();
				if(temp.getUid1().equals(uid)){
					user = userInfoDaoImpl.getUserInfoByUid(temp.getUid2());
				}
				else
				{
					user = userInfoDaoImpl.getUserInfoByUid(temp.getUid1());
				}
				info.add(user);
	        }
		}
		
		return json.accumulate("friends", info);
	}
}
