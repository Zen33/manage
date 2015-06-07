/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.common.page.PageInfo;
import fitfame.po.TopicInfo;

/**
 * @author zhangshu
 *
 */
public interface ITopicInfoDao {

	public List<TopicInfo> getNewTopicInfo(PageInfo page);
	
	public List<TopicInfo> getTopicInfoList(String uid, PageInfo page);
	
	public TopicInfo getTopicInfo(long id);
	
	public int insertTopicInfo(TopicInfo info);
	
	public int updateTopicInfo(TopicInfo info);
	
	public int deleteTopicInfo(long id);
}
