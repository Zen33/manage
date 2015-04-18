/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.common.page.PageInfo;
import fitfame.po.CoachInfo;

/**
 * @author zhangshu
 *
 */
public interface ICoachInfoDao {

	public List<CoachInfo> getCoachInfoList(PageInfo page);
	
	public CoachInfo getCoachInfo(String cid);
	
	public int insertCoachInfo(CoachInfo info);
	
	public int updateCoachInfo(CoachInfo info);
	
	public int deleteCoachInfo(String cid);
}
