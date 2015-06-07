/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.common.page.PageInfo;
import fitfame.po.TopicReply;

/**
 * @author zhangshu
 *
 */
public interface ITopicReplyDao {

	public List<TopicReply> getTopicReplyList(long tid, PageInfo page);
	
	public List<TopicReply> getUnreadTopicReply(String uid, PageInfo page);
	
	public int insertTopicReply(TopicReply info);
	
	public int updateTopicReply(TopicReply info);
	
	public int deleteTopicReply(long id);
	
	public int deleteAllTopicReply(long tid);
}
