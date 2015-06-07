/**
 * 
 */
package fitfame.dao;

import fitfame.po.StateInfo;

/**
 * @author zhangshu
 *
 */
public interface IStateInfoDao {

	public StateInfo getStateInfo(String uid);
	
	public int insertStateInfo(StateInfo info);
	
	public int updateStateInfo(StateInfo info);
	
	public int deleteStateInfo(String uid);
}
