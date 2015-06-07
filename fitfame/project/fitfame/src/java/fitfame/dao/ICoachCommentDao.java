/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.common.page.PageInfo;
import fitfame.po.CoachComment;

/**
 * @author zhangshu
 *
 */
public interface ICoachCommentDao {

	public List<CoachComment> getCoachCommentByCid(String cid, PageInfo page);
	
	public List<CoachComment> getUnreadCoachCommentByCid(String cid, PageInfo page);
	
	public int insertCoachComment(CoachComment comment);
	
	public int updateCoachComment(CoachComment comment);
	
	public int deleteCoachComment(long id);
}
