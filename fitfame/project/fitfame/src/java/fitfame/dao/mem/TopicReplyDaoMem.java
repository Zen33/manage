/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;
import org.springframework.stereotype.Repository;

import fitfame.common.dao.BaseDAO;
import fitfame.common.page.PageInfo;
import fitfame.dao.ITopicReplyDao;
import fitfame.po.TopicReply;

/**
 * @author zhangshu
 *
 */
@Repository
public class TopicReplyDaoMem extends BaseDAO<TopicReply> implements ITopicReplyDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#getTopicReplyList(long, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicReply> getTopicReplyList(long tid, PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#getUnreadTopicReply(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicReply> getUnreadTopicReply(String uid, PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#insertTopicReply(fitfame.po.TopicReply)
	 */
	@Override
	public int insertTopicReply(TopicReply info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#updateTopicReply(fitfame.po.TopicReply)
	 */
	@Override
	public int updateTopicReply(TopicReply info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#deleteTopicReply(long)
	 */
	@Override
	public int deleteTopicReply(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicReplyDao#deleteAllTopicReply(long)
	 */
	@Override
	public int deleteAllTopicReply(long tid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
