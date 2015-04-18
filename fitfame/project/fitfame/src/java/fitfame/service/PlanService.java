/**
 * 
 */
package fitfame.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fitfame.common.exception.BaseException;
import fitfame.common.exception.BaseServiceException;
import fitfame.common.util.DateUtil;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.ICoachPlanDao;
import fitfame.dao.ICoachPlanTemplateDao;
import fitfame.dao.IPersonalPlanDao;
import fitfame.dao.IPersonalSubPlanDao;
import fitfame.dao.IRelationPlanAndSplanDao;
import fitfame.dao.IRelationSplanAndDescDao;
import fitfame.dao.ISubPlanDao;
import fitfame.dao.ISubPlanDescDao;
import fitfame.po.CoachPlan;
import fitfame.po.CoachPlanTemplate;
import fitfame.po.PersonAndPlan;
import fitfame.po.PersonalPlan;
import fitfame.po.PersonalSubPlan;
import fitfame.po.RelationPlanAndSplan;
import fitfame.po.RelationSplanAndDesc;
import fitfame.po.SubPlan;
import fitfame.po.SubPlanDesc;

/**
 * @author zhangshu
 *
 */
@Service
public class PlanService {
	@Autowired(required = true)
	@Qualifier("coachPlanDaoImpl")
	private ICoachPlanDao coachPlanDao;
	
	@Autowired(required = true)
	@Qualifier("coachPlanTemplateDaoImpl")
	private ICoachPlanTemplateDao coachPlanTemplateDao;
	
	@Autowired(required = true)
	@Qualifier("personalPlanDaoImpl")
	private IPersonalPlanDao personalPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("personalSubPlanDaoImpl")
	private IPersonalSubPlanDao personalSubPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("subPlanDaoImpl")
	private ISubPlanDao subPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("subPlanDescDaoImpl")
	private ISubPlanDescDao subPlanDescDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("relationPlanAndSplanDaoImpl")
	private  IRelationPlanAndSplanDao relationPlanAndSplanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("relationSplanAndDescDaoImpl")
	private  IRelationSplanAndDescDao relationSplanAndDescDaoImpl;
	
	public JSONObject queryUserPlan(String uid){
		JSONObject json = new JSONObject();
		List<PersonalPlan> planlist = personalPlanDaoImpl.getPersonalPlanList(uid);
		List<PersonAndPlan> res = new ArrayList<PersonAndPlan>();
		if(planlist != null){
			for (int i = 0; i < planlist.size(); i++) {
				PersonalPlan person = planlist.get(i);
				CoachPlanTemplate plan = coachPlanTemplateDao.getCoachPlanTemplate(person.getPid());
				PersonAndPlan temp = new PersonAndPlan();
				temp.setPerson(person);
				temp.setPlan(plan);
				res.add(temp);
			}
		}
		return json.accumulate("plans", res);
	}
	
	public JSONObject querySubPlan(long id){
		JSONObject json = new JSONObject();
		List<SubPlan> planlist = subPlanDaoImpl.getSubPlanList(id);
		return json.accumulate("subplan", planlist);
	}

	public JSONObject queryUserSubPlan(String uid, long id) {
		JSONObject json = new JSONObject();
		List<SubPlan> planlist = subPlanDaoImpl.getSubPlanList(id);
		List<PersonalSubPlan> personlist = personalSubPlanDaoImpl
				.getPersonalSubPlanList(id);
		json.accumulate("subplan", planlist);
		json.accumulate("planproc", personlist);
		return json;
	}

	public JSONObject queryUserUndoSubPlan(String uid) {
		JSONObject json = new JSONObject();
		PersonalPlan plan = personalPlanDaoImpl.getUndoPersonalPlan(uid);
		
		if(plan != null){
			List<SubPlan> subplan = subPlanDaoImpl.getSubPlanList(plan.getPid());
			List<PersonalSubPlan> planproc = personalSubPlanDaoImpl.getPersonalSubPlanList(plan.getId());
			json.accumulate("subplan", subplan);
			json.accumulate("planproc", planproc);
			json.accumulate("pplan", plan);
			json.accumulate("tid", subplan.get(0).getCid());
		}
		else	
		{
			long edate = 2000000000000l;
			plan = personalPlanDaoImpl.getDonePersonalPlan(uid, edate);
			if(plan != null){
				List<SubPlan> subplan = subPlanDaoImpl.getSubPlanList(plan.getPid());
				List<PersonalSubPlan> planproc = personalSubPlanDaoImpl.getPersonalSubPlanList(plan.getId());
				json.accumulate("subplan", subplan);
				json.accumulate("planproc", planproc);
				json.accumulate("pplan", plan);
				json.accumulate("tid", subplan.get(0).getCid());
			}
		}
	    

		return json;
	}
	
	public JSONObject queryUserDoneSubPlan(String uid, long edate){
		JSONObject json = new JSONObject();
		PersonalPlan plan = personalPlanDaoImpl.getDonePersonalPlan(uid, edate);
		if(plan != null){
			List<SubPlan> subplan = subPlanDaoImpl.getSubPlanList(plan.getPid());
			List<PersonalSubPlan> planproc = personalSubPlanDaoImpl.getPersonalSubPlanList(plan.getId());
			json.accumulate("subplan", subplan);
			json.accumulate("planproc", planproc);
			json.accumulate("pplan", plan);
			json.accumulate("tid", subplan.get(0).getCid());
		}
		
		return json;
	}
	
	public JSONObject querySubPlanDesc(long sid){
		JSONObject json = new JSONObject();
		json.accumulate("desc", subPlanDescDaoImpl.getSubPlanDesc(sid));
		return json;
	}

	//需加回退锁
	public JSONObject updatePersonalPlanStep(String uid, long spid, int step) {
		PersonalSubPlan psplan = personalSubPlanDaoImpl.getPersonalSubPlan(spid);
		PersonalPlan pplan = personalPlanDaoImpl.getPersonalPlan(psplan.getPpid());
		JSONObject json = new JSONObject();
		if(psplan != null){
			RelationPlanAndSplan info = new RelationPlanAndSplan();
			info.setPid(pplan.getPid());
			info.setRank(psplan.getRank());
			info = relationPlanAndSplanDaoImpl.getRelationPlanAndSplan(info);
			int rank = relationSplanAndDescDaoImpl.getTopRankDesc(info.getSpid());
			
			if(psplan.getStep() + 1 == step)
			{
				psplan.setStep(step);				
			}			
			
			if(step == rank){
				long date = DateUtil.CurrentTime();
				psplan.setEdate(date);
				if(psplan.getRank() == relationPlanAndSplanDaoImpl.getTopRankSplan(pplan.getPid())){
					pplan.setEdate(date);
					personalPlanDaoImpl.updatePersonalPlan(pplan);
				}
			}
			
			personalSubPlanDaoImpl.updatePersonalSubPlan(psplan);
		}
		json = queryUserUndoSubPlan(uid);
		return json;
	}

	public JSONObject AddUserPlan(String myid, long pid) {
		JSONObject json = new JSONObject();
		PersonalPlan pplan = personalPlanDaoImpl.getUndoPersonalPlan(myid);
		if(pplan != null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.PlanExsits, myid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.PlanExsits, myid);
		}
		CoachPlanTemplate plan = coachPlanTemplateDao.getCoachPlanTemplate(pid);
		if(plan == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, myid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, myid);
		}
		PersonalPlan up = new PersonalPlan();
		up.setPid(pid);
		up.setUid(myid);
		up.setSdate(DateUtil.CurrentTime());
		up.setEdate(0);
		long ppid = personalPlanDaoImpl.insertPersonalPlan(up);
		
		List<SubPlan> subplan = subPlanDaoImpl.getSubPlanList(plan.getPid());
		for(SubPlan sp : subplan)
		{
			PersonalSubPlan psp = new PersonalSubPlan();
			psp.setDefaultValue();
			psp.setPpid(ppid);
			psp.setRank(sp.getRank());
			psp.setSdate(DateUtil.CurrentTime());
			personalSubPlanDaoImpl.insertPersonalSubPlan(psp);
		}
		json = queryUserUndoSubPlan(myid);
		return json;
	}

	public JSONObject RemoveUserPlan(String myid, long pid) {
		JSONObject json = new JSONObject();
		PersonalPlan pp = personalPlanDaoImpl.getPersonalPlan(pid);
		if(pp == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, myid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, myid);
		}
		personalPlanDaoImpl.deletePersonalPlan(pid);
		PersonalSubPlan psp = new PersonalSubPlan();
		psp.setPpid(pp.getId());
		personalSubPlanDaoImpl.deletePersonalSubPlan(psp);
		return json;
	}
		public JSONObject queryAllAvailablePlanDesc(String uid) {
		JSONObject json = new JSONObject();
		json.accumulate("own_desc", subPlanDescDaoImpl.getCoachOwnPlanDesc(uid));
		json.accumulate("share_desc", subPlanDescDaoImpl.getSharePlanDesc());
		return json;
	}

	public void addPlanDesc(String uid, String name, String intro, String pic,
			String url, int category, int quantity, String units, int duration,
			int share) {
		SubPlanDesc desc = new SubPlanDesc();
		desc.setCid(uid);
		desc.setDuration(duration);
		desc.setIntro(intro);
		desc.setInuse(0);
		desc.setName(name);
		desc.setPic(pic);
		desc.setQuantity(quantity);
		desc.setShare(share);
		desc.setUnits(units);
		subPlanDescDaoImpl.insertSubPlanDesc(desc);
	}

	public void updatePlanDesc(long sid, String intro, String pic, String url,
			int category, int quantity, String units, int duration, int share) {
		SubPlanDesc desc = new SubPlanDesc();
		desc.setId(sid);
		desc.setDuration(duration);
		desc.setIntro(intro);
		desc.setInuse(0);
		desc.setPic(pic);
		desc.setQuantity(quantity);
		desc.setShare(share);
		desc.setUnits(units);
		subPlanDescDaoImpl.updateSubPlanDesc(desc);
	}

	public JSONObject queryAllAvailableSubPlan(String uid) {
		JSONObject json = new JSONObject();
		json.accumulate("own_subplan", subPlanDaoImpl.getOwnSubPlanList(uid));
		json.accumulate("share_subplan", subPlanDaoImpl.getShareSubPlanList());
		return json;
	}

	public void assignDescToSubPlan(long pid, long did, int rank, int quantity,
			int duration){
		RelationSplanAndDesc info = new RelationSplanAndDesc();
		info.setDid(did);
		info.setDuration(duration);
		info.setQuantity(quantity);
		info.setRank(rank);
		info.setSpid(pid);
		relationSplanAndDescDaoImpl.insertRelationSplanAndDesc(info);
	}
	
	public void updateDescToSubPlan(long id, int rank, int quantity,
			int duration){
		RelationSplanAndDesc info = new RelationSplanAndDesc();
		info.setId(id);
		info.setDuration(duration);
		info.setQuantity(quantity);
		info.setRank(rank);
		relationSplanAndDescDaoImpl.updateRelationSplanAndDesc(info);
	}
	
	public void removeDescFromSubPlan(long id){
		relationSplanAndDescDaoImpl.deleteRelationSplanAndDesc(id);
	}
	
	public void addSubPlan(String cid, String name, String intro, String icon,
			int duration, int share) {
		SubPlan plan = new SubPlan();
		plan.setCid(cid);
		plan.setDuration(duration);
		plan.setIcon(icon);
		plan.setIntro(intro);
		plan.setInuse(0);
		plan.setName(name);
		plan.setRank(0);
		plan.setShare(share);
		subPlanDaoImpl.insertSubPlan(plan);
	}
	
	public void updateSubPlan(long pid, String intro, String icon,
			int duration, int share) {
		SubPlan plan = new SubPlan();
		plan.setId(pid);
		plan.setDuration(duration);
		plan.setIcon(icon);
		plan.setIntro(intro);
		plan.setInuse(0);
		plan.setShare(share);
		subPlanDaoImpl.updateSubPlan(plan);
	}
	
	public JSONObject queryAllPlanTemplate(String cid){
		JSONObject json = new JSONObject();
		return json.accumulate("plan", coachPlanTemplateDao.getCoachPlanTemplateList(cid));
	}
	
	public void assignSubPlanToPlan(long pid, long spid, int rank, 
			int duration){
		RelationPlanAndSplan info = new RelationPlanAndSplan();
		info.setDuration(duration);
		info.setPid(spid);
		info.setRank(rank);
		info.setSpid(spid);
		relationPlanAndSplanDaoImpl.insertRelationPlanAndSplan(info);
	}
	
	public void updateSubPlanToPlan(long id, int rank, 
			int duration){
		RelationPlanAndSplan info = new RelationPlanAndSplan();
		info.setDuration(duration);
		info.setRank(rank);
		info.setId(id);
		relationPlanAndSplanDaoImpl.insertRelationPlanAndSplan(info);
	}
	
	public void removeSubPlanFromPlan(long id){
		relationPlanAndSplanDaoImpl.deleteRelationPlanAndSplan(id);
	}
	
	public void addPlanTemplate(String cid, String name, String intro, String icon,
			int duration){
		CoachPlanTemplate info = new CoachPlanTemplate();
		info.setCid(cid);
		info.setDuration(duration);
		info.setIcon(icon);
		info.setIntro(intro);
		info.setInuse(0);
		info.setName(name);
		coachPlanTemplateDao.insertCoachPlanTemplate(info);
	}
	
	public void updatePlanTemplate(long id, String intro, String icon,
			int duration){
		CoachPlanTemplate info = new CoachPlanTemplate();
		info.setPid(id);
		info.setDuration(duration);
		info.setIcon(icon);
		info.setIntro(intro);
		info.setInuse(0);
		coachPlanTemplateDao.updateCoachPlanTemplate(info);
	}
	
	public JSONObject publishCoachPlan(long pid){
		JSONObject json = new JSONObject();
		CoachPlanTemplate plant = coachPlanTemplateDao.getCoachPlanTemplate(pid);
		if(plant == null)
			throw new BaseException(ExceptionIdUtil.TemplateNotExsits);
		
		CoachPlan plan = new CoachPlan();
		plan.setCid(plant.getCid());
		plan.setDuration(plant.getDuration());
		plan.setIcon(plant.getIcon());
		plan.setIntro(plant.getIntro());
		plan.setName(plant.getName());
		plan.setPid(plant.getPid());
		coachPlanDao.insertCoachPlan(plan);
		
		return json.accumulate("plans", coachPlanDao.getCoachPlanList(plan.getCid()));
	}
}
