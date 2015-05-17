/**
 * 
 */
package fitfame.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fitfame.common.util.TokenUtil;

import fitfame.dao.IVerifyInfoDao;

import fitfame.common.util.EMayUtil;
import fitfame.dao.mem.CheckIpMem;
import fitfame.po.VerifyInfo;

import fitfame.common.exception.BaseException;
import fitfame.common.exception.BaseServiceException;
import fitfame.common.util.DefaultValue;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.FileUtil;
import fitfame.common.util.HanziUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.ICoachAdInfoDao;
import fitfame.dao.ICoachInfoDao;
import fitfame.dao.ICoachPlanDao;
import fitfame.dao.ICoachServiceDao;
import fitfame.dao.IFriendInfoDao;
import fitfame.dao.IPersonalCoachDao;
import fitfame.dao.IPersonalPlanDao;
import fitfame.dao.IPersonalSubPlanDao;
import fitfame.dao.IStateInfoDao;
import fitfame.dao.ISubPlanDao;
import fitfame.dao.IUserInfoDao;
import fitfame.dao.IUserPassportDao;
import fitfame.po.CoachAdInfo;
import fitfame.po.CoachInfo;
import fitfame.po.CoachPlan;
import fitfame.po.CoachService;
import fitfame.po.CoachUser;
import fitfame.po.FriendInfo;
import fitfame.po.PersonalCoach;
import fitfame.po.PersonalPlan;
import fitfame.po.PersonalSubPlan;
import fitfame.po.StateInfo;
import fitfame.po.SubPlan;
import fitfame.po.SubPlanWithId;
import fitfame.po.UserInfo;
import fitfame.po.UserPassport;

/**
 * @author zhangshu
 * 
 */
@Service
public class UserService {
	@Autowired(required = true)
	@Qualifier("userPassportDaoImpl")
	private IUserPassportDao userPassportDaoImpl;

	@Autowired(required = true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;

	@Autowired(required = true)
	@Qualifier("stateInfoDaoImpl")
	private IStateInfoDao stateInfoDaoImpl;

	@Autowired(required = true)
	@Qualifier("friendInfoDaoImpl")
	private IFriendInfoDao friendInfoDaoImpl;
	
	@Autowired(required = true)
	private IVerifyInfoDao verifyInfoDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("coachInfoDaoImpl")
	private ICoachInfoDao coachInfoDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("personalCoachDaoImpl")
	private IPersonalCoachDao personalCoachDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("coachPlanDaoImpl")
	private ICoachPlanDao coachPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("coachAdInfoDaoImpl")
	private ICoachAdInfoDao coachAdInfoDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("personalPlanDaoImpl")
	private IPersonalPlanDao personalPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("subPlanDaoImpl")
	private ISubPlanDao subPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("personalSubPlanDaoImpl")
	private IPersonalSubPlanDao personalSubPlanDaoImpl;
	
	@Autowired(required = true)
	@Qualifier("coachServiceDaoImpl")
	private ICoachServiceDao coachServiceDaoImpl;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JSONObject registerUserPassport(String tel, String pw, String check) {
		JSONObject json = new JSONObject();
		String res = userPassportDaoImpl.ifUserPassportExist(tel);
		if (res == null) {
			VerifyInfo verify = verifyInfoDaoImpl.getVerifyInfo(tel);
			if (verify == null || !verify.getCheckNumber().equals(check)) {
				LogUtil.WriteLog(ExceptionIdUtil.VerifyError, tel + ";" + check);
				throw new BaseServiceException(ExceptionIdUtil.VerifyError, tel);
			}
			res = ProduceUid();
			UserPassport info = new UserPassport();
			info.setUid(res);
			info.setPw(pw);
			info.setTel(tel);
			userPassportDaoImpl.insertUserPassport(info);
			UserInfo user = new UserInfo();
			user.setUid(res);
			userInfoDaoImpl.insertUserInfo(user);
			StateInfo state = new StateInfo();
			state.setDefaultValue();
			state.setUid(res);
			stateInfoDaoImpl.insertStateInfo(state);
			json = UserBasicInfo(user);
			json.accumulate("token", TokenUtil.produceToken(res));
		} else {
			LogUtil.WriteLog(ExceptionIdUtil.UserExsits, tel);
			throw new BaseServiceException(ExceptionIdUtil.UserExsits, tel);
		}

		return json;
	}

	// 完善注册信息
	public JSONObject CompleteInfo(String uid, byte[] pic, String sex,
			String username, String picType, int brithday, int height,
			int weight, String city, String dist, int userType) {
		UserInfo user = new UserInfo();
		user.setUsername(username);
		user.setSex(sex);
        user.setUid(uid);
        user.setBrithday(brithday);
        user.setHeight(height);
        user.setWeight(weight);
        user.setCity(city);
        user.setDist(dist);
        user.setCategory(userType);
		// 将图片保存在本地，升本路径
		String icon = null;
		try {
			if (pic != null)
				icon = FileUtil.SaveStringAsHeaderPic(uid, pic, picType);
		} catch (IOException e) {
			LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, uid);
			throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, uid);
		}
		user.setIcon(icon);
		userInfoDaoImpl.updateUserInfo(user);
		return UserBasicInfo(user);
	}
	
	// 获取用户基本信息
	private JSONObject UserBasicInfo(UserInfo user) {
		JSONObject json = new JSONObject();
		JSONObject uJson = new JSONObject();
		uJson.accumulate("sex", user.getSex());
		uJson.accumulate("username", user.getUsername());
		uJson.accumulate("icon", user.getIcon());
		uJson.accumulate("birthday", user.getBrithday());
		uJson.accumulate("height", user.getHeight());
		uJson.accumulate("weight", user.getWeight());
		uJson.accumulate("uid", user.getUid());
		uJson.accumulate("firstLetter", GetFirstLetterFromName(user.getUsername()));
		json.accumulate("user_info", uJson);
		
		List<PersonalCoach> pc = personalCoachDaoImpl.getPersonalCoachList(user.getUid());
		if (pc != null && pc.size() > 0) {
			String cid = pc.get(0).getCid();
			CoachInfo coach = coachInfoDaoImpl.getCoachInfo(cid);
			if (coach == null)
				throw new BaseException(ExceptionIdUtil.CoachNotExsits);

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
			for(PersonalCoach item : pc)
			{
				CoachService cs = coachServiceDaoImpl.getCoachService(item.getSid());
				cs.setOffline(item.getOffline_unsign());
				cs.setOnline(item.getOnline_unsign());
				css.add(cs);
			}
			json.accumulate("services", css);			    
		}
		
		PersonalPlan plan = personalPlanDaoImpl.getUndoPersonalPlan(user.getUid());
		
		if(plan != null){
			List<SubPlanWithId> subplan = subPlanDaoImpl.getSubPlanList(plan.getPid());
			List<PersonalSubPlan> planproc = personalSubPlanDaoImpl.getPersonalSubPlanList(plan.getId());
			json.accumulate("subplan", subplan);
			json.accumulate("planproc", planproc);
			json.accumulate("pplan", plan);
			json.accumulate("tid", subplan.get(0).getCid());
		}
		return json;
	}

	private JSONObject UserBasicInfoWithoutPlan(UserInfo user) {
		JSONObject json = new JSONObject();
		JSONObject uJson = new JSONObject();
		uJson.accumulate("sex", user.getSex());
		uJson.accumulate("username", user.getUsername());
		uJson.accumulate("icon", user.getIcon());
		uJson.accumulate("birthday", user.getBrithday());
		uJson.accumulate("height", user.getHeight());
		uJson.accumulate("weight", user.getWeight());
		uJson.accumulate("uid", user.getUid());
		uJson.accumulate("firstLetter", GetFirstLetterFromName(user.getUsername()));
		json.accumulate("user_info", uJson);
		return json;
	}
	
	private String GetFirstLetterFromName(String name)
	{
		return HanziUtil.getFirstLetterOfName(name);
	}

	
	public JSONObject Login(String tel, String pw) {
		JSONObject json = new JSONObject();
		String uid = userPassportDaoImpl.checkUserPassport(tel, pw);
		if (uid == null) {
			LogUtil.WriteLog(ExceptionIdUtil.PwError, tel);
			throw new BaseServiceException(ExceptionIdUtil.PwError, tel);
		}
		
		UserInfo user = userInfoDaoImpl.getUserInfoByUid(uid);
		json = UserBasicInfo(user);
		json.accumulate("state", queryStateInfo(uid));
		json.accumulate("token", TokenUtil.produceToken(uid));
		if(user.getCategory() == 1)
		{
			json.accumulate("coach_info", coachInfoDaoImpl.getCoachInfo(user.getUid()));
			json.accumulate("coach_ad", coachAdInfoDaoImpl.getCoachAdInfoList(user.getUid()));	
		}
		return json;
	}

	public JSONObject queryUserInfo(String uid, String visitor, long flag) {
		JSONObject json = new JSONObject();
		UserInfo user = userInfoDaoImpl.getUserInfoByUid(visitor);
		if(flag == 0)
		{
			json = UserBasicInfo(user);

			FriendInfo info = new FriendInfo();
			info.setUid1(uid);
			info.setUid2(visitor);
			if (uid != null && friendInfoDaoImpl.getFriendInfo(info) != null)
				json.accumulate("friend", "1");
			else
				json.accumulate("friend", "2");
		}
		else
		{
			json = UserBasicInfoWithoutPlan(user);
		}
		return json;
	}

	public StateInfo queryStateInfo(String uid) {
		StateInfo info = stateInfoDaoImpl.getStateInfo(uid);
		if (info == null) {
			throw new BaseServiceException(ExceptionIdUtil.UserNotExsits, uid);
		}

		return info;
	}

	private String ProduceUid() {
		// 1号服务器，其他服务器此处需改动
		String result = "" + DefaultValue.defaultUserHeadId;
		for (int i = 0; i < 7; i++) {
			Random random = new Random();
			int r = random.nextInt(10);
			result += r;
		}
		return result;
	}

	public void Verify(String tel, String ip) {
		// 判断是否已经注册
		if (userPassportDaoImpl.ifUserPassportExist(tel) != null) {
			LogUtil.WriteLog(ExceptionIdUtil.UserExsits, tel);
			throw new BaseServiceException(ExceptionIdUtil.UserExsits, tel);
		}
		//对ip进行处理
		if(!CheckIpMem.CheckIp(ip))
		{
			LogUtil.WriteLog(ExceptionIdUtil.IpWarn, tel);
			throw new BaseServiceException(ExceptionIdUtil.IpWarn, tel);
		}
		// 判断验证次数是否超过每天限制
		VerifyInfo verify = verifyInfoDaoImpl.getVerifyInfo(tel);
		if (verify == null) {
			verify = new VerifyInfo();
			verify.setDefaultValue();
			verify.setVerifyCount(DefaultValue.VerifyCountLimit);
			verify.setPhone(tel);
			verify.setCheckNumber(ProduceCheck());
			verify.setPwCount(DefaultValue.FindPwLimit);
			verifyInfoDaoImpl.insertVerifyInfo(verify);
			EMayUtil.SendMessage(tel, "【英巢互动】美道注册码：" + verify.getCheckNumber());
		} else {
			if (verify.getVerifyCount() < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.OverVerifyCount, tel);
				throw new BaseServiceException(ExceptionIdUtil.OverVerifyCount,
						tel);
			}
			verify.setVerifyCount(verify.getVerifyCount() - 1);
			verify.setCheckNumber(ProduceCheck());
			verifyInfoDaoImpl.updateVerifyInfo(verify);
			EMayUtil.SendMessage(tel, "【英巢互动】美道注册码：" + verify.getCheckNumber());
		}
	}
	
	private String ProduceCheck() {
		String result = "";
		for (int i = 0; i < 7; i++) {
			Random random = new Random();
			int r = random.nextInt(10);
			result += r;
		}
		return result;
	}
	
	public void FindPw(String tel) {
		// 判断是否已经注册
		String uid = userPassportDaoImpl.ifUserPassportExist(tel);
		if (uid == null) {
			LogUtil.WriteLog(ExceptionIdUtil.UserNotExsits, tel);
			throw new BaseServiceException(ExceptionIdUtil.UserNotExsits, tel);
		}
		// 判断验证次数是否超过每天限制
		VerifyInfo verify = verifyInfoDaoImpl.getVerifyInfo(tel);
		if (verify == null) {
			verify = new VerifyInfo();
			verify.setDefaultValue();
			verify.setVerifyCount(DefaultValue.VerifyCountLimit);
			verify.setPhone(tel);
			verify.setRandomPw(ProduceCheck());
			verify.setPwCount(DefaultValue.FindPwLimit);
			verifyInfoDaoImpl.insertVerifyInfo(verify);
			EMayUtil.SendMessage(tel, "【英巢互动】拼乐新密码：" + verify.getRandomPw());
		} else {
			if (verify.getPwCount() < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.OverFindPwCount, tel);
				throw new BaseServiceException(ExceptionIdUtil.OverFindPwCount,
						tel);
			}
			verify.setPwCount(verify.getPwCount() - 1);
			verify.setRandomPw(ProduceCheck());
			verifyInfoDaoImpl.updateVerifyInfo(verify);
			EMayUtil.SendMessage(tel, "【英巢互动】拼乐新密码：" + verify.getRandomPw());
		}
		UserPassport up = new UserPassport();
		up.setPw(verify.getRandomPw());
		up.setTel(tel);
		up.setUid(uid);
		userPassportDaoImpl.updateUserPassport(up);
	}

	public JSONObject ChangePw(String myid, String npw) {
		JSONObject json = new JSONObject();
		UserPassport up = userPassportDaoImpl.getUserPassport(myid);
		if(up == null )
		{
			LogUtil.WriteLog(ExceptionIdUtil.PwError, myid);
			throw new BaseServiceException(ExceptionIdUtil.PwError,
					"");
		}
		up.setPw(npw);
		userPassportDaoImpl.updateUserPassport(up);
		return json;
	}

	public JSONObject CoachCompleteInfo(String uid, File icon, String sex,
			String username, String picType, int brithday, int height,
			int weight, String city, String dist, int category, List<File> ads,
			List<String> adsType, String intro, int exp) {
		JSONObject json = new JSONObject();
		UserInfo user = userInfoDaoImpl.getUserInfoByUid(uid);
		user.setBrithday(brithday);
		user.setCategory(1);
		user.setCity(city);
		user.setDist(dist);
		user.setHeight(height);
		user.setWeight(weight);
		user.setSex(sex);
		user.setUsername(username);
		
		String url = null;
		try {
			if (icon != null)
				url = FileUtil.SaveHeadFile(uid, icon, picType);
		} catch (IOException e) {
			LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, uid);
			throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, uid);
		}
		user.setIcon(url);
		userInfoDaoImpl.updateUserInfo(user);
		
		coachAdInfoDaoImpl.deleteCoachAdInfo(uid);
		
		for(int i = 0; i < ads.size(); i ++)
		{
			File ad = ads.get(i);
			String adUrl = null;
			try {
				if (ad != null)
					adUrl = FileUtil.SaveADFile(uid, ad, adsType.get(i));
			} catch (IOException e) {
				LogUtil.WriteLog(ExceptionIdUtil.MediaLordFail, uid);
				throw new BaseServiceException(ExceptionIdUtil.MediaLordFail, uid);
			}
			CoachAdInfo cai = new CoachAdInfo();
			cai.setCid(uid);
			cai.setUrl(adUrl);
			coachAdInfoDaoImpl.insertCoachAdInfo(cai);
		}
		
		CoachInfo coachInfo = coachInfoDaoImpl.getCoachInfo(uid);
		coachInfo.setExp(exp);
		coachInfo.setIntro(intro);
		coachInfoDaoImpl.updateCoachInfo(coachInfo);
		
		
		JSONObject pjson = UserBasicInfo(user);
		pjson.accumulate("state", queryStateInfo(uid));
		pjson.accumulate("token", TokenUtil.produceToken(uid));
		
		pjson.accumulate("coach_info", coachInfoDaoImpl.getCoachInfo(user.getUid()));
		pjson.accumulate("coach_ad", coachAdInfoDaoImpl.getCoachAdInfoList(user.getUid()));
		json.accumulate("success_jsonpCallback", pjson);
		
		return json;
	}

}
