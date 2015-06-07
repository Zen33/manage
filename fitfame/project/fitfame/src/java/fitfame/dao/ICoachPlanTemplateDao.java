/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CoachPlanTemplate;

/**
 * @author zhangshu
 *
 */
public interface ICoachPlanTemplateDao {

	public List<CoachPlanTemplate> getCoachPlanTemplateList(String cid);
	
	public CoachPlanTemplate getCoachPlanTemplate(long pid);
	
	public int insertCoachPlanTemplate(CoachPlanTemplate plan);
	
	public int updateCoachPlanTemplate(CoachPlanTemplate plan);
	
	public int deleteCoachPlanTemplate(long pid);
}
