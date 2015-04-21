/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.SubPlanDesc;
import fitfame.po.SubPlanDescWithId;

/**
 * @author zhangshu
 *
 */
public interface ISubPlanDescDao {

	public List<SubPlanDescWithId> getSubPlanDesc(long sid);
	
	public List<SubPlanDesc> getCoachOwnPlanDesc(String uid);
	
	public List<SubPlanDesc> getSharePlanDesc();
	
	public SubPlanDesc getSubPlanDescWithId(long id);
	
	public int insertSubPlanDesc(SubPlanDesc info);
	
	public int updateSubPlanDesc(SubPlanDesc info);
	
	public int deleteSubPlanDesc(SubPlanDesc info);
}
