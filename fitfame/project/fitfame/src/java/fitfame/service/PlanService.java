/**
 * 
 */
package fitfame.service;

import java.io.File;
import java.io.IOException;
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
import fitfame.common.util.FileUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.ICoachPlanDao;
import fitfame.dao.ICoachPlanTemplateDao;
import fitfame.dao.IPersonalCoachDao;
import fitfame.dao.IPersonalPlanDao;
import fitfame.dao.IPersonalSubPlanDao;
import fitfame.dao.IRelationPlanAndSplanDao;
import fitfame.dao.IRelationSplanAndDescDao;
import fitfame.dao.ISubPlanDao;
import fitfame.dao.ISubPlanDescDao;
import fitfame.dao.IUserInfoDao;
import fitfame.po.CoachPlan;
import fitfame.po.CoachPlanTemplate;
import fitfame.po.PersonAndPlan;
import fitfame.po.PersonalCoach;
import fitfame.po.PersonalPlan;
import fitfame.po.PersonalSubPlan;
import fitfame.po.RelationPlanAndSplan;
import fitfame.po.RelationSplanAndDesc;
import fitfame.po.SubPlan;
import fitfame.po.SubPlanDesc;
import fitfame.po.SubPlanWithId;
import fitfame.po.UserInfo;

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
	private IRelationPlanAndSplanDao relationPlanAndSplanDaoImpl;

	@Autowired(required = true)
	@Qualifier("relationSplanAndDescDaoImpl")
	private IRelationSplanAndDescDao relationSplanAndDescDaoImpl;

	@Autowired(required = true)
	@Qualifier("personalCoachDaoImpl")
	private IPersonalCoachDao personalCoachDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;

	public JSONObject queryUserPlan(String uid) {
		JSONObject json = new JSONObject();
		List<PersonalPlan> planlist = personalPlanDaoImpl
				.getPersonalPlanList(uid);
		List<PersonAndPlan> res = new ArrayList<PersonAndPlan>();
		if (planlist != null) {
			for (int i = 0; i < planlist.size(); i++) {
				PersonalPlan person = planlist.get(i);
				CoachPlanTemplate plan = coachPlanTemplateDao
						.getCoachPlanTemplate(person.getPid());
				PersonAndPlan temp = new PersonAndPlan();
				temp.setPerson(person);
				temp.setPlan(plan);
				res.add(temp);
			}
		}
		return json.accumulate("plans", res);
	}

	public JSONObject querySubPlan(long id) {
		JSONObject json = new JSONObject();
		List<SubPlanWithId> planlist = subPlanDaoImpl.getSubPlanList(id);
		return json.accumulate("subplan", planlist);
	}
	
	public JSONObject QueryUserSubPlan(String myid, String uid) {
		JSONObject json = new JSONObject();
		PersonalPlan plan = personalPlanDaoImpl.getUndoPersonalPlan(uid);

		if (plan != null) {
			List<SubPlanWithId> subplan = subPlanDaoImpl
					.getSubPlanList(plan.getPid());
			List<PersonalSubPlan> planproc = personalSubPlanDaoImpl
					.getPersonalSubPlanList(plan.getId());
			JSONArray jArray = new JSONArray();
			for(int i = 0; i < subplan.size(); i++)
			{
				JSONObject pJson = new JSONObject();
				pJson.accumulate("id", subplan.get(i).getId());
				pJson.accumulate("duration", subplan.get(i).getDuration());
				pJson.accumulate("name", subplan.get(i).getName());
				pJson.accumulate("intro", subplan.get(i).getIntro());
				pJson.accumulate("rank", subplan.get(i).getRank());
				pJson.accumulate("rid", subplan.get(i).getRid());
				pJson.accumulate("done", planproc.get(i).getEdate() != 0? 1:0);
				jArray.add(pJson);
			}
			json.accumulate("subplan", jArray);
		}

		return json;
	}

	public JSONObject queryUserUndoSubPlan(String uid) {
		JSONObject json = new JSONObject();
		PersonalPlan plan = personalPlanDaoImpl.getUndoPersonalPlan(uid);

		if (plan != null) {
			List<SubPlanWithId> subplan = subPlanDaoImpl
					.getSubPlanList(plan.getPid());
			List<PersonalSubPlan> planproc = personalSubPlanDaoImpl
					.getPersonalSubPlanList(plan.getId());
			json.accumulate("subplan", subplan);
			json.accumulate("planproc", planproc);
			json.accumulate("pplan", plan);
			json.accumulate("tid", subplan.get(0).getCid());
		} else {
			long edate = 2000000000000l;
			plan = personalPlanDaoImpl.getDonePersonalPlan(uid, edate);
			if (plan != null) {
				List<SubPlanWithId> subplan = subPlanDaoImpl.getSubPlanList(plan
						.getPid());
				List<PersonalSubPlan> planproc = personalSubPlanDaoImpl
						.getPersonalSubPlanList(plan.getId());
				json.accumulate("subplan", subplan);
				json.accumulate("planproc", planproc);
				json.accumulate("pplan", plan);
				json.accumulate("tid", subplan.get(0).getCid());
			}
		}

		return json;
	}

	public JSONObject queryUserDoneSubPlan(String uid, long edate) {
		JSONObject json = new JSONObject();
		PersonalPlan plan = personalPlanDaoImpl.getDonePersonalPlan(uid, edate);
		if (plan != null) {
			List<SubPlanWithId> subplan = subPlanDaoImpl
					.getSubPlanList(plan.getPid());
			List<PersonalSubPlan> planproc = personalSubPlanDaoImpl
					.getPersonalSubPlanList(plan.getId());
			json.accumulate("subplan", subplan);
			json.accumulate("planproc", planproc);
			json.accumulate("pplan", plan);
			json.accumulate("tid", subplan.get(0).getCid());
		}

		return json;
	}

	public JSONObject querySubPlanDesc(long sid) {
		JSONObject json = new JSONObject();
		json.accumulate("desc", subPlanDescDaoImpl.getSubPlanDesc(sid));
		return json;
	}

	// 需加回退锁
	public JSONObject updatePersonalPlanStep(String uid, long spid, int step) {
		PersonalSubPlan psplan = personalSubPlanDaoImpl
				.getPersonalSubPlan(spid);
		PersonalPlan pplan = personalPlanDaoImpl.getPersonalPlan(psplan
				.getPpid());
		JSONObject json = new JSONObject();
		if (psplan != null) {
			RelationPlanAndSplan info = new RelationPlanAndSplan();
			info.setPid(pplan.getPid());
			info.setRank(psplan.getRank());
			info = relationPlanAndSplanDaoImpl.getRelationPlanAndSplan(info);
			int rank = relationSplanAndDescDaoImpl.getTopRankDesc(info
					.getSpid());

			if (psplan.getStep() + 1 == step) {
				psplan.setStep(step);
			}

			if (step == rank) {
				long date = DateUtil.CurrentTime();
				psplan.setEdate(date);
				if (psplan.getRank() == relationPlanAndSplanDaoImpl
						.getTopRankSplan(pplan.getPid())) {
					pplan.setEdate(date);
					personalPlanDaoImpl.updatePersonalPlan(pplan);
				}
			}

			personalSubPlanDaoImpl.updatePersonalSubPlan(psplan);
		}
		json = queryUserUndoSubPlan(uid);
		return json;
	}

	public JSONObject ReplaceUserPlan(String cid, long pid, String uid) {
		// 判断是否是私人教练
		List<PersonalCoach> coachs = personalCoachDaoImpl
				.getPersonalCoachList(uid);
		if (coachs.size() != 0 && !coachs.get(0).getUid().equals(uid)) {
			LogUtil.WriteLog(ExceptionIdUtil.Quan, cid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, cid);
		}

		PersonalPlan pplan = personalPlanDaoImpl.getUndoPersonalPlan(uid);
		if(pplan != null)
		    personalPlanDaoImpl.deletePersonalPlan(pplan.getId());

		AddUserPlan(uid, pid);
		return QueryUserSubPlan(cid,uid);
	}

	public JSONObject AddUserPlan(String myid, long pid) {
		JSONObject json = new JSONObject();
		PersonalPlan pplan = personalPlanDaoImpl.getUndoPersonalPlan(myid);
		if (pplan != null) {
			LogUtil.WriteLog(ExceptionIdUtil.PlanExsits, myid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.PlanExsits, myid);
		}
		CoachPlanTemplate plan = coachPlanTemplateDao.getCoachPlanTemplate(pid);
		if (plan == null) {
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, myid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, myid);
		}
		PersonalPlan up = new PersonalPlan();
		up.setPid(pid);
		up.setUid(myid);
		up.setSdate(DateUtil.CurrentTime());
		up.setEdate(0);
		long ppid = personalPlanDaoImpl.insertPersonalPlan(up);

		List<SubPlanWithId> subplan = subPlanDaoImpl.getSubPlanList(plan.getPid());
		for (SubPlan sp : subplan) {
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
		if (pp == null) {
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
		List<SubPlanDesc> allPlan = new ArrayList<SubPlanDesc>();
		allPlan.addAll(subPlanDescDaoImpl.getCoachOwnPlanDesc(uid));
		allPlan.addAll(subPlanDescDaoImpl.getSharePlanDesc());
		json.accumulate("desc", allPlan);
		return json;
	}
	
	public void updatePlanDesc(long id, String intro, File pic, int category,
			int quantity, String units, int duration, int share, File pic2,
			String picType, File media, String mediaType, String myid, String name) {
		SubPlanDesc desc = new SubPlanDesc();
		desc.setCid(myid);
		desc.setId(id);
		desc.setDuration(duration);
		desc.setIntro(intro);
		desc.setInuse(0);
		desc.setName(name);
		
		String picUrl = null;
		try {
			if (pic != null)
				picUrl = FileUtil.SaveDescFile(myid + "suolue", pic, picType);
		} catch (IOException e) {
			LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, myid);
			throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, myid);
		}
		
		desc.setPic(picUrl);
		
		String mediaUrl = null;
		try {
			if (media != null)
				mediaUrl = FileUtil.SaveDescFile(myid, media, mediaType);
		} catch (IOException e) {
			LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, myid);
			throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, myid);
		}
		
		desc.setUrl(mediaUrl);
		desc.setQuantity(quantity);
		desc.setShare(1);
		desc.setUnits(units);
		subPlanDescDaoImpl.updateSubPlanDesc(desc);
	}

	public void addPlanDesc(String myid, String name, String intro, File pic,
			int category, int quantity, String units, int duration, int share,
			File pic2, String picType, File media, String mediaType) {
		SubPlanDesc desc = new SubPlanDesc();
		desc.setCid(myid);
		desc.setDuration(duration);
		desc.setIntro(intro);
		desc.setInuse(0);
		desc.setName(name);
		
		String picUrl = null;
		try {
			if (pic != null)
				picUrl = FileUtil.SaveDescFile(myid + "suolue", pic, picType);
		} catch (IOException e) {
			LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, myid);
			throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, myid);
		}
		
		desc.setPic(picUrl);
		
		String mediaUrl = null;
		try {
			if (media != null)
				mediaUrl = FileUtil.SaveDescFile(myid, media, mediaType);
		} catch (IOException e) {
			LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, myid);
			throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, myid);
		}
		
		desc.setUrl(mediaUrl);
		desc.setQuantity(quantity);
		desc.setShare(1);
		desc.setUnits(units);
		subPlanDescDaoImpl.insertSubPlanDesc(desc);
	}
	
	public JSONObject RemoveCoachPlanDesc(long id, String myid) {
		JSONObject json = new JSONObject();
		SubPlanDesc desc = subPlanDescDaoImpl.getSubPlanDescWithId(id);
		if(desc == null || !desc.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		
		subPlanDescDaoImpl.deleteSubPlanDesc(desc);
		json = queryAllAvailablePlanDesc(myid);
		return json;
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
		desc.setShare(1);
		desc.setUnits(units);
		subPlanDescDaoImpl.updateSubPlanDesc(desc);
	}

	public JSONObject queryAllAvailableSubPlan(String uid) {
		JSONObject json = new JSONObject();
		List<SubPlan> allPlan = new ArrayList<SubPlan>();
		allPlan.addAll(subPlanDaoImpl.getOwnSubPlanList(uid));
		allPlan.addAll(subPlanDaoImpl.getShareSubPlanList());

		json.accumulate("subplan", allPlan);
		return json;
	}

	public JSONObject assignDescToSubPlan(String myid, long pid, long did) {
		JSONObject json = new JSONObject();
		SubPlan sp = subPlanDaoImpl.getSubPlan(pid);
		if(sp == null || sp.getShare() == 0)
		{
			LogUtil.WriteLog(ExceptionIdUtil.CantChangePublic, pid + "");
			throw new BaseServiceException(ExceptionIdUtil.CantChangePublic, pid + "");
		}
		if(!sp.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, pid + "");
			throw new BaseServiceException(ExceptionIdUtil.Quan, pid + "");
		}
		
		SubPlanDesc desc = subPlanDescDaoImpl.getSubPlanDescWithId(did);
		if(desc == null || (!desc.getCid().equals(myid) && desc.getShare() ==1))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, pid + "");
			throw new BaseServiceException(ExceptionIdUtil.Quan, pid + "");
		}
		
		int rank = relationSplanAndDescDaoImpl.getTopRankDesc(pid);
		
		RelationSplanAndDesc info = new RelationSplanAndDesc();
		info.setDid(did);
		info.setDuration(desc.getDuration());
		info.setQuantity(desc.getQuantity());
		info.setRank(rank + 1);
		info.setSpid(pid);
		relationSplanAndDescDaoImpl.insertRelationSplanAndDesc(info);
		
		json = querySubPlanDesc(pid);
		return json;
	}

	public void updateDescToSubPlan(long id, int rank, int quantity,
			int duration) {
		RelationSplanAndDesc info = new RelationSplanAndDesc();
		info.setId(id);
		info.setDuration(duration);
		info.setQuantity(quantity);
		info.setRank(rank);
		relationSplanAndDescDaoImpl.updateRelationSplanAndDesc(info);
	}

	public JSONObject removeDescFromSubPlan(String myid, long id) {
		JSONObject json = new JSONObject();
		RelationSplanAndDesc relation = relationSplanAndDescDaoImpl.getRelationSplanAndDesc(id);
		
		if(relation == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		
		SubPlan sub = subPlanDaoImpl.getSubPlan(relation.getSpid());
		
		if(!sub.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		
		relationSplanAndDescDaoImpl.deleteRelationSplanAndDesc(id);
		relationSplanAndDescDaoImpl.updateRelationSplanAndDescRank(relation.getRank(), relation.getSpid());
		json = querySubPlanDesc(relation.getSpid());
		return json;
	}

	public void addSubPlan(String cid, String name, String intro,
			int duration) {
		SubPlan plan = new SubPlan();
		plan.setCid(cid);
		plan.setDuration(duration);
		plan.setIcon(null);
		plan.setIntro(intro);
		plan.setInuse(0);
		plan.setName(name);
		plan.setRank(0);
		plan.setShare(1);
		subPlanDaoImpl.insertSubPlan(plan);
	}
	
	public JSONObject deleteSubPlan(String myid, long id) {
		JSONObject json = new JSONObject();
		SubPlan plan = subPlanDaoImpl.getSubPlan(id);
		if(plan == null || !plan.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		subPlanDaoImpl.deleteSubPlan(id);
		
		json = queryAllAvailableSubPlan(myid);
		return json;
	}

	public void updateSubPlan(String myid,long pid, String intro, 
			int duration,String name) {
		SubPlan plan = subPlanDaoImpl.getSubPlan(pid);
		if(plan == null || !plan.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		plan.setId(pid);
		plan.setName(name);
		plan.setDuration(duration);
		plan.setIcon(null);
		plan.setIntro(intro);
		plan.setInuse(0);
		plan.setShare(1);
		subPlanDaoImpl.updateSubPlan(plan);
	}

	public JSONObject queryAllPlanTemplate(String cid) {
		JSONObject json = new JSONObject();
		return json.accumulate("plan",
				coachPlanTemplateDao.getCoachPlanTemplateList(cid));
	}

	public JSONObject queryAllPlan(String myid) {
		JSONObject json = new JSONObject();
		return json.accumulate("plan", coachPlanDao.getCoachPlanList(myid));
	}

	public JSONObject assignSubPlanToPlan(long pid, long spid, int rank, String myid) {
		JSONObject json = new JSONObject();
		CoachPlanTemplate plan = coachPlanTemplateDao.getCoachPlanTemplate(pid);
		if (plan == null || !plan.getCid().equals(myid)) {
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, myid + ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, myid);
		}
		SubPlan sub = subPlanDaoImpl.getSubPlan(spid);
		if(sub == null || (!sub.getCid().equals(myid) && sub.getShare() ==1))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, pid + "");
			throw new BaseServiceException(ExceptionIdUtil.Quan, pid + "");
		}
		
		RelationPlanAndSplan info = new RelationPlanAndSplan();
		info.setDuration(sub.getDuration());
		info.setPid(pid);
		info.setRank(rank);
		info.setSpid(spid);
		relationPlanAndSplanDaoImpl.insertRelationPlanAndSplan(info);
		json = querySubPlan(pid);
		return json;
	}

	public void updateSubPlanToPlan(long id, int rank, int duration) {
		RelationPlanAndSplan info = new RelationPlanAndSplan();
		info.setDuration(duration);
		info.setRank(rank);
		info.setId(id);
		relationPlanAndSplanDaoImpl.insertRelationPlanAndSplan(info);
	}

	public JSONObject removeSubPlanFromPlan(long id, String myid) {
		JSONObject json = new JSONObject();
		RelationPlanAndSplan relation = relationPlanAndSplanDaoImpl.getRelationPlanAndSplanWithId(id);
		if(relation == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		
		CoachPlanTemplate plan = coachPlanTemplateDao.getCoachPlanTemplate(relation.getPid());
		
		if(!plan.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.Quan, myid);
			throw new BaseServiceException(ExceptionIdUtil.Quan, myid);
		}
		
		
		relationPlanAndSplanDaoImpl.deleteRelationPlanAndSplan(id);
		json = querySubPlan(relation.getPid());
		return json;
	}

	public void addPlanTemplate(String cid, String name, String intro,
			String icon, int duration) {
		CoachPlanTemplate info = new CoachPlanTemplate();
		info.setCid(cid);
		info.setDuration(duration);
		info.setIcon(icon);
		info.setIntro(intro);
		info.setInuse(0);
		info.setName(name);
		coachPlanTemplateDao.insertCoachPlanTemplate(info);
	}
	
	public JSONObject deletePlanTemplate(String myid, long id) {
		JSONObject json = new JSONObject();
		CoachPlanTemplate info = coachPlanTemplateDao.getCoachPlanTemplate(id);
		if(info == null || !info.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, ";" + id);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, "" + id);
		}
		
		coachPlanTemplateDao.deleteCoachPlanTemplate(id);
		
		json = queryAllPlanTemplate(myid);
		return json;
	}

	public void updatePlanTemplate(String myid, long id, String intro, String icon,
			int duration, String name) {
		CoachPlanTemplate info = coachPlanTemplateDao.getCoachPlanTemplate(id);
		if(info == null || !info.getCid().equals(myid))
		{
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, ";" + id);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, "" + id);
		}
		info.setPid(id);
		info.setDuration(duration);
		info.setIcon(icon);
		info.setIntro(intro);
		info.setInuse(0);
		info.setName(name);
		coachPlanTemplateDao.updateCoachPlanTemplate(info);
	}

	public JSONObject publishCoachPlan(long pid, String myid) {
		JSONObject json = new JSONObject();
		CoachPlanTemplate plant = coachPlanTemplateDao
				.getCoachPlanTemplate(pid);
		if (plant == null || !plant.getCid().equals(myid)) {
			LogUtil.WriteLog(ExceptionIdUtil.TemplateNotExsits, ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.TemplateNotExsits,
					"" + pid);
		}

		CoachPlan plan = coachPlanDao.getCoachPlan(pid);
		if (plan != null) {
			LogUtil.WriteLog(ExceptionIdUtil.AlreadyPublish, ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.AlreadyPublish, ""
					+ pid);
		}

		plan = new CoachPlan();
		plan.setCid(plant.getCid());
		plan.setDuration(plant.getDuration());
		plan.setIcon(plant.getIcon());
		plan.setIntro(plant.getIntro());
		plan.setName(plant.getName());
		plan.setPid(plant.getPid());
		coachPlanDao.insertCoachPlan(plan);

		return json.accumulate("plan",
				coachPlanDao.getCoachPlanList(plan.getCid()));
	}

	public JSONObject RemoveCoachPlan(long pid, String myid) {
		JSONObject json = new JSONObject();
		CoachPlan plan = coachPlanDao.getCoachPlan(pid);
		if (plan == null || !plan.getCid().equals(myid)) {
			LogUtil.WriteLog(ExceptionIdUtil.NoPlan, ";" + pid);
			throw new BaseServiceException(ExceptionIdUtil.NoPlan, "" + pid);
		}

		coachPlanDao.deleteCoachPlan(pid);
		return json.accumulate("plan",
				coachPlanDao.getCoachPlanList(plan.getCid()));
	}

	public JSONObject queryCoachUser(String myid) {
		JSONObject json = new JSONObject();
		List<PersonalCoach> coachs = personalCoachDaoImpl.getPersonalCoachs(myid);
		JSONArray jArray= new JSONArray();
		for(PersonalCoach person : coachs)
		{
			UserInfo user = userInfoDaoImpl.getUserInfoByUid(person.getUid());
			jArray.add(user);
		}
		return json.accumulate("users", jArray);
	}
}
