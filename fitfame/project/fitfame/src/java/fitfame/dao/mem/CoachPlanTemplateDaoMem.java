/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ICoachPlanTemplateDao;
import fitfame.po.CoachPlanTemplate;

/**
 * @author zhangshu
 *
 */
@Repository
public class CoachPlanTemplateDaoMem implements ICoachPlanTemplateDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#getCoachPlanTemplateList(java.lang.String)
	 */
	@Override
	public List<CoachPlanTemplate> getCoachPlanTemplateList(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#getCoachPlanTemplate(long)
	 */
	@Override
	public CoachPlanTemplate getCoachPlanTemplate(long pid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#insertCCoachPlanTemplate(fitfame.po.CoachPlanTemplate)
	 */
	@Override
	public int insertCoachPlanTemplate(CoachPlanTemplate plan) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see fitfame.dao.ICoachPlanTemplateDao#updateCoachPlanTemplate(fitfame.po.CoachPlanTemplate)
	 */
	@Override
	public int updateCoachPlanTemplate(CoachPlanTemplate plan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteCoachPlanTemplate(long pid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
