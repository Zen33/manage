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
import fitfame.common.util.LogUtil;
import fitfame.dao.IFavorTopicDao;
import fitfame.dao.ISquareAdInfoDao;
import fitfame.dao.ITopicInfoDao;
import fitfame.dao.ITopicReplyDao;
import fitfame.dao.IUserInfoDao;
import fitfame.po.SquareAdInfo;
import fitfame.po.TopicInfo;
import fitfame.po.TopicReply;
import fitfame.po.UserInfo;
import fitfame.po.UserReply;
import fitfame.po.UserTopic;
import fitfame.po.FavorTopic;

/**
 * @author zhangshu
 *
 */
@Service
public class ActivityService {
	@Autowired(required=true)
	@Qualifier("topicInfoDaoImpl")
	private ITopicInfoDao topicInfoDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("topicReplyDaoImpl")
	private ITopicReplyDao topicReplyDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("squareAdInfoDaoImpl")
	private ISquareAdInfoDao squareAdInfoDaoImpl;
	
	@Autowired(required=true)
	private IFavorTopicDao favorTopicDaoImpl;
	
	public JSONObject queryPublicActivityInfo(PageInfo page){
		JSONObject json = new JSONObject();
		List<TopicInfo> topic = topicInfoDaoImpl.getNewTopicInfo(page);
		List<UserTopic> res = new ArrayList<UserTopic>();
		for(int i = 0; i < topic.size(); i++)  
        {  
			TopicInfo info = topic.get(i);
			UserInfo user = userInfoDaoImpl.getUserInfoByUid(info.getUid());
			UserTopic ut = new UserTopic();
			ut.setTopic(info);
			ut.setUser(user);
			res.add(ut);
        }
		
		json.accumulate("topic", res);
		List<SquareAdInfo> ad = squareAdInfoDaoImpl.getSquareAdInfoList();
		json.accumulate("ad", ad);
		
		JSONObject pJson = new JSONObject();
		pJson.accumulate("pageSize", page.getPageSize());
		pJson.accumulate("pageNo", page.getCurrentPageNo());
		pJson.accumulate("totalNo", page.getTotalCount());
		json.accumulate("pageInfo", pJson);
		return json;
	}
	
	public JSONObject queryOnesActivityInfo(String uid, PageInfo page){
		JSONObject json = new JSONObject();
		List<TopicInfo> topic = topicInfoDaoImpl.getTopicInfoList(uid, page);
		List<UserTopic> res = new ArrayList<UserTopic>();
		for(int i = 0; i < topic.size(); i++)  
        {  
			TopicInfo info = topic.get(i);
			UserInfo user = userInfoDaoImpl.getUserInfoByUid(info.getUid());
			UserTopic ut = new UserTopic();
			ut.setTopic(info);
			ut.setUser(user);
			res.add(ut);
        }
		
		JSONObject pJson = new JSONObject();
		pJson.accumulate("pageSize", page.getPageSize());
		pJson.accumulate("pageNo", page.getCurrentPageNo());
		pJson.accumulate("totalNo", page.getTotalCount());
		json.accumulate("pageInfo", pJson);
		
		return json.accumulate("topic", res);
	}
	
	public JSONObject queryTopicReplyInfo(long tid, PageInfo page){
		JSONObject json = new JSONObject();
		List<TopicReply> reply = topicReplyDaoImpl.getTopicReplyList(tid, page);
		List<UserReply> res = new ArrayList<UserReply>();
		for(int i = 0; i < reply.size(); i++)  
        {  
			TopicReply info = reply.get(i);
			UserInfo user = userInfoDaoImpl.getUserInfoByUid(info.getUid());
			UserReply ur = new UserReply();
			ur.setReply(info);
			ur.setUser(user);
			res.add(ur);
        }
		
		JSONObject pJson = new JSONObject();
		pJson.accumulate("pageSize", page.getPageSize());
		pJson.accumulate("pageNo", page.getCurrentPageNo());
		pJson.accumulate("totalNo", page.getTotalCount());
		json.accumulate("pageInfo", pJson);
		
		return json.accumulate("reply", res);
	}
	
	public JSONObject queryUnreadReplyInfo(String uid, PageInfo page){
		JSONObject json = new JSONObject();
		List<TopicReply> reply = topicReplyDaoImpl.getUnreadTopicReply(uid, page);
		List<UserReply> res = new ArrayList<UserReply>();
		for(int i = 0; i < reply.size(); i++)  
        {  
			TopicReply info = reply.get(i);
			UserInfo user = userInfoDaoImpl.getUserInfoByUid(info.getUid());
			TopicInfo topic = topicInfoDaoImpl.getTopicInfo(info.getTotid());
			UserReply ur = new UserReply();
			ur.setReply(info);
			ur.setUser(user);
			ur.setTopic(topic);
			res.add(ur);
        }
		for(int i = 0; i < reply.size(); i++)
		{
			TopicReply info = reply.get(i);
			info.setIfread(0);
			topicReplyDaoImpl.updateTopicReply(info);
		}
		JSONObject pJson = new JSONObject();
		pJson.accumulate("pageSize", page.getPageSize());
		pJson.accumulate("pageNo", page.getCurrentPageNo());
		pJson.accumulate("totalNo", page.getTotalCount());
		json.accumulate("pageInfo", pJson);
		json.accumulate("reply", res);
		return json;
	}
	
	public void createNewTopic(TopicInfo topic){
		topicInfoDaoImpl.insertTopicInfo(topic);
	}
	
	public void createNewReply(TopicReply reply){
		TopicInfo topic = topicInfoDaoImpl.getTopicInfo(reply.getTotid());
		reply.setTouid(topic.getUid());
		if(reply.getUid().equals(reply.getTouid()))
		{
			reply.setIfread(0);
		}
		else	
		{
			reply.setIfread(1);
		}
		topicReplyDaoImpl.insertTopicReply(reply);
		topic.setReply(topic.getReply() + 1);
		topicInfoDaoImpl.updateTopicInfo(topic);
	}

	public JSONObject FavorTopic(String uid, long tid) {
		JSONObject json = new JSONObject();
		FavorTopic info = new FavorTopic();
		info.setTid(tid);
		info.setUid(uid);
		FavorTopic fav = favorTopicDaoImpl.getFavorTopic(info);
		if(fav != null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.AlreadyFav, uid + ";" + tid);
			throw new BaseServiceException(ExceptionIdUtil.AlreadyFav, uid);
		}
		TopicInfo topic = topicInfoDaoImpl.getTopicInfo(tid);
		if(topic == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.NoTopic, uid + ";" + tid);
			throw new BaseServiceException(ExceptionIdUtil.NoTopic, uid);
		}
		favorTopicDaoImpl.insertFavorTopic(info);
		topic.setFav(topic.getFav() + 1);
		topicInfoDaoImpl.updateTopicInfo(topic);
		json.accumulate("fav", topic.getFav());
		return json;
	}
}
