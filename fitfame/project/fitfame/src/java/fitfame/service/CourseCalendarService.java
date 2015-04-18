/**
 * 
 */
package fitfame.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fitfame.common.exception.BaseException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.dao.ICoachServiceDao;
import fitfame.dao.ICommonCalendarDao;
import fitfame.dao.ICourseCalendarDao;
import fitfame.dao.ICourseMemberDao;
import fitfame.dao.IPersonalCoachDao;
import fitfame.dao.IUserInfoDao;
import fitfame.po.CoachService;
import fitfame.po.CommonCalendar;
import fitfame.po.CourseAndCalendar;
import fitfame.po.CourseCalendar;
import fitfame.po.CourseMember;
import fitfame.po.PersonalCoach;
import fitfame.po.UserInfo;

/**
 * @author zhangshu
 *
 */
@Service
public class CourseCalendarService {
	@Autowired(required=true)
	@Qualifier("courseCalendarDaoImpl")
	private ICourseCalendarDao courseCalendarDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("courseMemberDaoImpl")
	private ICourseMemberDao courseMemberDaoImpl; 
	
	@Autowired(required=true)
	@Qualifier("commonCalendarDaoImpl")
	private ICommonCalendarDao commonCalendarDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("coachServiceDaoImpl")
	private ICoachServiceDao coachServiceDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("userInfoDaoImpl")
	private IUserInfoDao userInfoDaoImpl;
	
	@Autowired(required=true)
	@Qualifier("personalCoachDaoImpl")
	private IPersonalCoachDao personalCoachDaoImpl;
	
	
	public JSONObject getCourseCalendar(String cid, int month){
		JSONObject json = new JSONObject();
		List<CommonCalendar> calendar = commonCalendarDaoImpl.getMonthCalendar(month);
		if(calendar == null)
			throw new BaseException(ExceptionIdUtil.CalendarNotExsits);
		
		json.accumulate("calendar", calendar);
		List<CourseCalendar> courseList = courseCalendarDaoImpl.getMonthCourseCalendar(cid, month);
		List<CourseAndCalendar> ccList = new ArrayList<CourseAndCalendar>();
		if(courseList != null){
			for (int i = 0; i < courseList.size(); i++) {
				CourseCalendar course = courseList.get(i);
				CourseAndCalendar cc = new CourseAndCalendar();
				cc.setCalendar(course);
				CoachService service = coachServiceDaoImpl.getCoachService(course.getSid());
				cc.setService(service);
				ccList.add(cc);
			}
		}
		
		json.accumulate("course", ccList);
		return json;
	}

	//加时间判断
	public JSONObject addCourseCalendar(String cid, long cdate, int maxlimit, long sid, int stype){
		JSONObject json = new JSONObject();
		CoachService service  = coachServiceDaoImpl.getCoachService(sid);
		if (service == null)
			throw new BaseException(ExceptionIdUtil.ServiceNotExsits);
		
		int minutes = 0;
		if (stype == 0){
			minutes = service.getOnline();
		}else{
			minutes = service.getOffline();
		}
		
		if (minutes == 0)
			throw new BaseException(ExceptionIdUtil.ServiceNotExsits);
			
		CourseCalendar preCalendar = courseCalendarDaoImpl.getPreviousCourseCalendar(cid, cdate);
		if (preCalendar != null && preCalendar.getCdate()+preCalendar.getMinutes()>cdate)
			throw new BaseException(ExceptionIdUtil.OutOfTimeLimit);
		
		CourseCalendar nextCalendar = courseCalendarDaoImpl.getNextCourseCalendar(cid, cdate);
		if (nextCalendar!=null && cdate+minutes>nextCalendar.getCdate())
			throw new BaseException(ExceptionIdUtil.OutOfTimeLimit);
		
		CourseCalendar calendar = new CourseCalendar();
		calendar.setCid(cid);
		calendar.setCdate(cdate);
		calendar.setMaxlimit(maxlimit);
		calendar.setMinutes(minutes);
		calendar.setPublished(0);
		calendar.setSid(sid);
		calendar.setSign(0);
		calendar.setStype(stype);
		long id = courseCalendarDaoImpl.insertCourseCalendar(calendar);
		json.accumulate("id", id);
		return json;
	}
	
	public JSONObject refreshCourseCalendar(long id, int maxlimit){
		JSONObject json = new JSONObject();
		CourseCalendar cc = courseCalendarDaoImpl.getCourseCalendar(id);
		if(cc == null)
			throw new BaseException(ExceptionIdUtil.RecordNotExsits);

		if(cc.getSign() > maxlimit)
			throw new BaseException(ExceptionIdUtil.LessThanAssign);
		
		CourseCalendar calendar = new CourseCalendar();
		calendar.setId(id);
		calendar.setMaxlimit(maxlimit);
		courseCalendarDaoImpl.updateCourseCalendar(calendar);
		calendar = courseCalendarDaoImpl.getCourseCalendar(calendar.getId());
		json.accumulate("calendar", calendar);
		return json;
	}
	
	public void removeCourseCalendar(long id){
		CourseCalendar cc = courseCalendarDaoImpl.getCourseCalendar(id);
		if(cc == null)
			throw new BaseException(ExceptionIdUtil.RecordNotExsits);
		
		if(cc.getSign() > 0)
			throw new BaseException(ExceptionIdUtil.CannotRemoveWithMember);
		
		courseCalendarDaoImpl.deleteCourseCalendar(id);
	}
	
	public JSONObject getCourseMember(long cid, int type){
		JSONObject json = new JSONObject();
		List<CourseMember> members = courseMemberDaoImpl.getCourseMemberList(cid);
		List<UserInfo> users = new ArrayList<UserInfo>();
		List<PersonalCoach> coachList = null;
		if (type ==1){
			//线下
			coachList = personalCoachDaoImpl.getPersonCoachByOfflineService(cid);
		}else{
			//线下
			coachList = personalCoachDaoImpl.getPersonCoachByOnlineService(cid);
		}
		
		if(members!= null){
			for(int i = 0; i < members.size(); i++){
				CourseMember member = members.get(i);
				UserInfo user = userInfoDaoImpl.getUserInfoByUid(member.getUid());
				users.add(user);
			}
		}
		
		json.accumulate("member", users);
		users.clear();
		if(coachList != null){
			for(int i = 0; i < coachList.size(); i++){
				PersonalCoach coach = coachList.get(i);
				if(members!=null){
					for(int j=0; j< members.size(); j++){
						CourseMember member = members.get(j);
						if(coach.getUid().equals(member.getUid()))
							continue;
					}
				}
				UserInfo user = userInfoDaoImpl.getUserInfoByUid(coach.getUid());
				users.add(user);
			}
		}
		
		json.accumulate("candidate", users);
		
		return json;
	}
	
	//需加事物
	public JSONObject assignAndUnassignCourseMember(long id, String[] assign, String[] unassign){
		JSONObject json = new JSONObject();
		CourseCalendar calendar = courseCalendarDaoImpl.getCourseCalendar(id);
		int deep = assign.length-unassign.length;
		if(deep >0 && calendar.getMaxlimit()-calendar.getSign()<deep)
			throw new BaseException(ExceptionIdUtil.OutofMaxLimit);
		
		for(int i=0; i<unassign.length; i++){
			String uid = unassign[i];
			unassignCalendarMember(id, uid, calendar.getStype());
		}
		
		for(int i=0; i<assign.length; i++){
			String uid = assign[i];
			assignCalendarMember(id, uid, calendar.getStype());
		}
		
		calendar = courseCalendarDaoImpl.getCourseCalendar(id);
		
		return json.accumulate("calendar", calendar);
	}
	
	//需加事物
	private void unassignCalendarMember(long id, String uid, int type){
		CourseMember member = new CourseMember();
		member.setCid(id);
		member.setUid(uid);
		courseMemberDaoImpl.deleteCourseMember(member);
		CourseCalendar calendar = courseCalendarDaoImpl.getCourseCalendar(id);
		int signed = calendar.getSign()-1;
		if(signed < 0)
			throw new BaseException(ExceptionIdUtil.NotLessThanZero);
		
		calendar.setSign(signed);
		courseCalendarDaoImpl.updateCourseCalendar(calendar);
		PersonalCoach coach = personalCoachDaoImpl.getPersonalCoach(uid, calendar.getSid());
	    
		if(type == 1){
			//线下
			int count = coach.getOffline_unsign();
			count++;
			if(count > coach.getOffline())
				throw new BaseException(ExceptionIdUtil.OutOfRestLimit);
			
			coach.setOffline_unsign(count);
		}
		else
		{
			//线上
			int count = coach.getOnline_unsign();
			count++;
			if(count > coach.getOnline())
				throw new BaseException(ExceptionIdUtil.OutOfRestLimit);
			
			coach.setOnline_unsign(count);
		}
		
		personalCoachDaoImpl.updatePersonalCoach(coach);
	}
	
	//需加事物
	private void assignCalendarMember(long id, String uid, int type){
		CourseMember member = new CourseMember();
		member.setCid(id);
		member.setUid(uid);
		courseMemberDaoImpl.insertCourseMember(member);
		CourseCalendar calendar = courseCalendarDaoImpl.getCourseCalendar(id);
		int signed = calendar.getSign()+1;
		if(signed > calendar.getMaxlimit())
			throw new BaseException(ExceptionIdUtil.OutofMaxLimit);
		
		calendar.setSign(signed);
		courseCalendarDaoImpl.updateCourseCalendar(calendar);
		PersonalCoach coach = personalCoachDaoImpl.getPersonalCoach(uid, calendar.getSid());
	    
		if(type == 1){
			//线下
			int count = coach.getOffline_unsign();
			count--;
			if(count < 0)
				throw new BaseException(ExceptionIdUtil.NotLessThanZero);
			
			coach.setOffline_unsign(count);
		}
		else
		{
			//线上
			int count = coach.getOnline_unsign();
			count--;
			if(count < 0)
				throw new BaseException(ExceptionIdUtil.NotLessThanZero);
			
			coach.setOnline_unsign(count);
		}
		
		personalCoachDaoImpl.updatePersonalCoach(coach);
	}
}
