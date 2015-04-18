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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fitfame.common.exception.BaseException;
import fitfame.common.exception.BaseServiceException;
import fitfame.common.page.PageInfo;
import fitfame.common.util.DateUtil;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.dao.ICoachAdInfoDao;
import fitfame.dao.ICoachCommentDao;
import fitfame.dao.ICoachInfoDao;
import fitfame.dao.ICoachPlanDao;
import fitfame.dao.ICoachServiceDao;
import fitfame.dao.IFavCoachDao;
import fitfame.dao.IPersonalCoachDao;
import fitfame.dao.IUserInfoDao;
import fitfame.po.CoachAdInfo;
import fitfame.po.CoachComment;
import fitfame.po.CoachInfo;
import fitfame.po.CoachPlan;
import fitfame.po.CoachService;
import fitfame.po.CoachUser;
import fitfame.po.FavCoach;
import fitfame.po.PersonalCoach;
import fitfame.po.TopicReply;
import fitfame.po.UserInfo;
import fitfame.po.UserReply;

/**
 * @author zhangshu
 * 
 */
@Service
public class CoachCommonService {
	@Autowired(required = true)
	@Qualifier("coachInfoDaoImpl")
	private ICoachInfoDao coachInfoDaoImpl;

	@Autowired(required = true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;

	@Autowired(required = true)
	@Qualifier("coachPlanDaoImpl")
	private ICoachPlanDao coachPlanDaoImpl;

	@Autowired(required = true)
	@Qualifier("personalCoachDaoImpl")
	private IPersonalCoachDao personalCoachDaoImpl;

	
	@Autowired(required = true)
	@Qualifier("coachServiceDaoImpl")
	private ICoachServiceDao coachServiceDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("favCoachDaoImpl")
	private IFavCoachDao favCoachDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("coachAdInfoDaoImpl")
	private ICoachAdInfoDao coachAdInfoDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("coachCommentDaoImpl")
	private ICoachCommentDao coachCommentDaoImpl;
	
	public JSONObject queryCoachInfo(String cid) {
		JSONObject json = new JSONObject();
		CoachInfo coach = coachInfoDaoImpl.getCoachInfo(cid);
		if (coach == null)
			throw new BaseException(ExceptionIdUtil.CoachNotExsits);

		UserInfo info = userInfoDaoImpl.getUserInfoByUid(cid);
		List<CoachPlan> plan = coachPlanDaoImpl.getCoachPlanList(cid);
		CoachUser cu = new CoachUser();
		cu.setCoach(coach);
		cu.setUser(info);
		List<CoachAdInfo> ad = coachAdInfoDaoImpl.getCoachAdInfoList(cid);
		json.accumulate("coach", cu);
		json.accumulate("plan", plan);
		json.accumulate("ad", ad);
		return json;
	}

	public JSONObject queryCoachInfoList(PageInfo page) {
		JSONObject json = new JSONObject();
		List<CoachInfo> coach = coachInfoDaoImpl.getCoachInfoList(page);
		JSONArray jArray = new JSONArray();
		if (coach != null) {
			for (int i = 0; i < coach.size(); i++) {
				CoachInfo info = coach.get(i);
				CoachUser cu = new CoachUser();
				cu.setCoach(info);
				cu.setUser(userInfoDaoImpl.getUserInfoByUid(info.getCid()));
				JSONObject cJson = new JSONObject();
				cJson.accumulate("coach", cu);
				jArray.add(cJson);
			}
		}

		json.accumulate("coaches", jArray);
		
		JSONObject pJson = new JSONObject();
		pJson.accumulate("pageSize", page.getPageSize());
		pJson.accumulate("pageNo", page.getCurrentPageNo());
		pJson.accumulate("totalNo", page.getTotalCount());
		json.accumulate("pageInfo", pJson);
		return json;
	}

	public JSONObject queryPersonalCoachInfo(String myid, String uid) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		List<PersonalCoach> pc = personalCoachDaoImpl.getPersonalCoachList(uid);
		if (pc != null && pc.size() > 0) {
			String cid = pc.get(0).getCid();
			CoachInfo coach = coachInfoDaoImpl.getCoachInfo(cid);
			if (coach == null)
				throw new BaseException(ExceptionIdUtil.CoachNotExsits);

			UserInfo info = userInfoDaoImpl.getUserInfoByUid(cid);
			List<CoachPlan> plan = coachPlanDaoImpl.getCoachPlanList(cid);
			CoachUser cu = new CoachUser();
			cu.setCoach(coach);
			cu.setUser(info);
			json.accumulate("coach", cu);
			json.accumulate("plan", plan);
			json.accumulate("fav", isFavCoach(myid, coach.getCid()));
			
			List<CoachService> css = new ArrayList<CoachService>();
			for(PersonalCoach item : pc)
			{
				CoachService cs = coachServiceDaoImpl.getCoachService(item.getSid());
				css.add(cs);
			}
			json.accumulate("services", css);
		}
		return json;
	}

	public String isFavCoach(String uid, String cid){
		String res = "1";
		FavCoach fc = new FavCoach();
		fc.setCid(cid);
		fc.setUid(uid);
		if(favCoachDaoImpl.getFavCoach(fc) == null)
		{
			res = "2";
		}
		
		return res;
	}
	
	public JSONObject addFavCoach(String uid, String cid){
		JSONObject json = new JSONObject();
		CoachInfo coach = coachInfoDaoImpl.getCoachInfo(cid);
		if (coach == null)
			throw new BaseException(ExceptionIdUtil.CoachNotExsits);
		FavCoach fc = new FavCoach();
		fc.setCid(cid);
		fc.setUid(uid);
		if(favCoachDaoImpl.getFavCoach(fc) != null)
			throw new BaseServiceException(ExceptionIdUtil.FavCoachRepeatError,cid);
		
		favCoachDaoImpl.insertFavCoach(fc);
		coach.setFav(coach.getFav() + 1);
		coachInfoDaoImpl.updateCoachInfo(coach);
		json.accumulate("fav", coach.getFav());
		return json;
	}
	
	public JSONObject queryCoachAllService(String cid) {
		JSONObject json = new JSONObject();
		List<CoachService> res = coachServiceDaoImpl.getCoachServiceList(cid);
		return json.accumulate("services", res);
	}
		public JSONObject AddCoachService(String cid, String name, String intro,
			int cost, int online_times, int offline_times, int online,
			int offline) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		CoachService service = new CoachService();
		service.setCid(cid);
		service.setCost(cost);
		service.setIntro(intro);
		service.setName(name);
		service.setNum(0);
		service.setOffline(offline);
		service.setOffline_times(offline_times);
		service.setOnline(online);
		service.setOnline_times(online_times);
		service.setInuse(0);
		long res = coachServiceDaoImpl.insertCoachService(service);
		return json.accumulate("sid", res);
	}

	public void updateCoachService(long sid, String intro,
			int cost, int online_times, int offline_times, int online,
			int offline) {
		// TODO Auto-generated method stub
		CoachService service = new CoachService();
		service.setSid(sid);
		service.setCost(cost);
		service.setIntro(intro);
		service.setOffline(offline);
		service.setOffline_times(offline_times);
		service.setOnline(online);
		service.setOnline_times(online_times);
		coachServiceDaoImpl.updateCoachService(service);
	}

	public void removeCoachService(long sid){
		coachServiceDaoImpl.deleteCoachService(sid);
	}
	
	public void addCoachInfo(CoachInfo coach) {
		coachInfoDaoImpl.insertCoachInfo(coach);
	}

	public void deleteCoachInfo(String cid) {
		coachInfoDaoImpl.deleteCoachInfo(cid);
	}

	public void updateCoachInfo(CoachInfo coach) {
		coachInfoDaoImpl.updateCoachInfo(coach);
	}

	public void createNewReply(CoachComment cc) {
		CoachInfo coach = coachInfoDaoImpl.getCoachInfo(cc.getCid());
		if (coach == null)
			throw new BaseException(ExceptionIdUtil.CoachNotExsits);
		if(cc.getCid().equals(cc.getUid()))
		{
			cc.setIfead(0);
		}
		else
		{
			cc.setIfead(1);
		}
		coachCommentDaoImpl.insertCoachComment(cc);
		coach.setReply(coach.getReply() + 1);
		coachInfoDaoImpl.updateCoachInfo(coach);
	}

	public JSONObject queryCoachReplyInfo(String cid, PageInfo page) {
		JSONObject json = new JSONObject();
		List<CoachComment> reply = coachCommentDaoImpl.getCoachCommentByCid(cid, page);
		List<UserReply> res = new ArrayList<UserReply>();
		for(int i = 0; i < reply.size(); i++)  
        {  
			CoachComment info = reply.get(i);
			UserInfo user = userInfoDaoImpl.getUserInfoByUid(info.getUid());
			UserReply ur = new UserReply();
			ur.setCoachReply(info);
			ur.setUser(user);
			res.add(ur);
        }
		
		JSONObject pJson = new JSONObject();
		pJson.accumulate("pageSize", page.getPageSize());
		pJson.accumulate("pageNo", page.getCurrentPageNo());
		pJson.accumulate("totalNo", page.getTotalCount());
		json.accumulate("pageInfo", pJson);
		
		return json.accumulate("reply", res);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JSONObject addPersonalCoachService(String uid, String cid, long[] sids){
		JSONObject json = new JSONObject();
		List<PersonalCoach> pcs = personalCoachDaoImpl.getPersonalCoachList(uid);
		for(int i = 0; i < sids.length; i ++)
		{
			long sid = sids[i];
			CoachService cs = coachServiceDaoImpl.getCoachService(sid);
			
			if(cs == null){
				throw new BaseException(ExceptionIdUtil.NoService);
			}
			PersonalCoach pc = personalCoachDaoImpl.getPersonalCoach(uid, sid);
			if(pc != null)
			{
				throw new BaseException(ExceptionIdUtil.ServeRepeat);
			}			
			
			if(pcs != null && pcs.size() > 0)
			{
				if(!pcs.get(0).getCid().equals(cs.getCid()))
				{
					throw new BaseException(ExceptionIdUtil.CoachRepeat);
				}
			}
			
			pc = new PersonalCoach();
			pc.setUid(uid);
			pc.setCid(cid);
			pc.setSid(sid);
			pc.setPrice(cs.getCost());
			pc.setOnline(cs.getOnline());
			pc.setOffline(cs.getOffline());
			pc.setOffline_unsign(cs.getOffline());
			pc.setOnline_unsign(cs.getOnline());
			pc.setSdate(DateUtil.CurrentTime());
			pc.setEdate(DateUtil.CurrentTime() + cs.getMonth() * 30 * 24 * 60 * 60 * 1000);
			
			personalCoachDaoImpl.insertPersonalCoach(pc);
			pcs.add(pc);
			
			cs.setNum(cs.getNum() + 1);
			coachServiceDaoImpl.updateCoachService(cs);
		}
		
		if (pcs != null && pcs.size() > 0) {
			CoachInfo coach = coachInfoDaoImpl.getCoachInfo(cid);

			UserInfo info = userInfoDaoImpl.getUserInfoByUid(cid);
			List<CoachPlan> plan = coachPlanDaoImpl.getCoachPlanList(cid);
			
			List<CoachAdInfo> ad = coachAdInfoDaoImpl.getCoachAdInfoList(cid);
			
			CoachUser cu = new CoachUser();
			cu.setCoach(coach);
			cu.setUser(info);
			json.accumulate("coach", cu);
			json.accumulate("plan", plan);
			json.accumulate("ad", ad);
			
			List<CoachService> css = new ArrayList<CoachService>();
			for(PersonalCoach item : pcs)
			{
				CoachService cs = coachServiceDaoImpl.getCoachService(item.getSid());
				cs.setOffline(item.getOffline_unsign());
				cs.setOnline(item.getOnline_unsign());
				css.add(cs);
			}
			json.accumulate("services", css);			    
		}
		    
		
		return json;
	}
	
	public JSONObject completePersonalCoachService(String uid, String cid, long sid, int onl, int offl){
		JSONObject json = new JSONObject();
		PersonalCoach ps = personalCoachDaoImpl.getPersonalCoach(uid,sid);
		
		if(ps == null){
			throw new BaseException(ExceptionIdUtil.NoService);
		}
		
		int onlineLeft = ps.getOnline_unsign();
		if(onl > 0 && onlineLeft > onl)
			onlineLeft -= onl;
		
		int offlineLeft = ps.getOffline_unsign();
		if(offl > 0 && offlineLeft > offl)
			offlineLeft -= offl;
		
		ps.setOnline_unsign(onlineLeft);
		ps.setOffline_unsign(offlineLeft);
		
		if(onlineLeft > 0 || offlineLeft > 0)
		    personalCoachDaoImpl.updatePersonalCoach(ps);
		else
			personalCoachDaoImpl.deletePersonalCoach(ps);
		
		List<PersonalCoach> pcs = personalCoachDaoImpl.getPersonalCoachList(uid);
		List<CoachService> css = new ArrayList<CoachService>();
		for(PersonalCoach item : pcs)
		{
			CoachService cs = coachServiceDaoImpl.getCoachService(item.getSid());
			cs.setOffline(item.getOffline_unsign());
			cs.setOnline(item.getOnline_unsign());
			css.add(cs);
		}
		json.accumulate("services", css);
		return json;
	}

	public JSONObject VerifyPersonalCoachService(String uid, String cid,
			long[] sids) {
		JSONObject json = new JSONObject();
		List<PersonalCoach> pcs = personalCoachDaoImpl.getPersonalCoachList(uid);
		for(int i = 0; i < sids.length; i ++)
		{
			long sid = sids[i];
			CoachService cs = coachServiceDaoImpl.getCoachService(sid);
			
			if(cs == null){
				throw new BaseException(ExceptionIdUtil.NoService);
			}
			PersonalCoach pc = personalCoachDaoImpl.getPersonalCoach(uid, sid);
			if(pc != null)
			{
				throw new BaseException(ExceptionIdUtil.ServeRepeat);
			}			
			
			if(pcs != null && pcs.size() > 0)
			{
				if(!pcs.get(0).getCid().equals(cid))
				{
					throw new BaseException(ExceptionIdUtil.CoachRepeat);
				}
			}
		}	
		String code = uid;
		for(int i = 0; i<sids.length; i ++)
		{
			code += sids[i] + "A";
		}
		code += DateUtil.CurrentTime();
		json.accumulate("code", code);
		return json;
	}
}
