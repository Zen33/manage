/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.common.page.PageInfo;
import fitfame.dao.ITopicInfoDao;
import fitfame.po.TopicInfo;

/**
 * @author zhangshu
 *
 */
@Repository
public class TopicInfoDaoMem implements ITopicInfoDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#getNewTopicInfo(fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicInfo> getNewTopicInfo(PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#getTopicInfoList(java.lang.String, fitfame.common.page.PageInfo)
	 */
	@Override
	public List<TopicInfo> getTopicInfoList(String uid, PageInfo page) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#insertTopicInfo(fitfame.po.TopicInfo)
	 */
	@Override
	public int insertTopicInfo(TopicInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#updateTopicInfo(fitfame.po.TopicInfo)
	 */
	@Override
	public int updateTopicInfo(TopicInfo info) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ITopicInfoDao#deleteTopicInfo(long)
	 */
	@Override
	public int deleteTopicInfo(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TopicInfo getTopicInfo(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
