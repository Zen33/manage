/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CoachAdInfo;

/**
 * @author zhangshu
 *
 */
public interface ICoachAdInfoDao {

	public List<CoachAdInfo> getCoachAdInfoList(String cid);
	
	public int insertCoachAdInfo(CoachAdInfo info);
	
	public int deleteCoachAdInfo(String url);
}
