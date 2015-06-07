/**
 * 
 */
package fitfame.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fitfame.common.exception.BaseException;
import fitfame.common.util.DateUtil;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.dao.ICoachServiceDao;
import fitfame.dao.ICommonCalendarDao;
import fitfame.dao.ICourseCalendarDao;
import fitfame.dao.ICourseMemberDao;
import fitfame.dao.IPersonalCoachDao;
import fitfame.dao.IUserInfoDao;
import fitfame.po.CoachService;
import fitfame.po.CommonCalendar;
import fitfame.po.CommonCalendarWithNum;
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
	
	
	public JSONObject getCourseCalendar(String cid, int page){
		JSONObject json = new JSONObject();
		//找到第一个周一的日期
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.getNowDate());
		int curweek = cal.get(Calendar.DAY_OF_WEEK);
		int offset = 0;
		if (curweek == 1) {
		    // 星期天
		    offset = 6;
		} else {
		    // 星期一至星期六
		    offset = curweek - 2;
		}
		if(page > 0)
		{
			cal.setTime(DateUtil.getAfterDate(offset + page * 28));
		}
		else
		{
			page = page * -1;
			cal.setTime(DateUtil.getBeforeDate(offset + page * 28));
		}
		
		int beforeYear = cal.get(Calendar.YEAR);
		int beforemonth=cal.get(Calendar.MONTH)+1;   
		int beforeday =cal.get(Calendar.DAY_OF_MONTH);
		int firstDay = beforeYear * 10000 + beforemonth * 100 + beforeday;
		
		cal.setTime(DateUtil.getAfterDate(cal.getTime(), 28));
		int lastYear = cal.get(Calendar.YEAR);
		int lastmonth=cal.get(Calendar.MONTH)+1;   
		int lastday =cal.get(Calendar.DAY_OF_MONTH);
		int endDay = lastYear * 10000 + lastmonth * 100 + lastday;
		
		List<CommonCalendar> calendar = commonCalendarDaoImpl.getMonthCalendar(firstDay,endDay);
		if(calendar == null)
			throw new BaseException(ExceptionIdUtil.CalendarNotExsits);
		List<CommonCalendarWithNum> ccnList = new ArrayList<CommonCalendarWithNum>();
		for(CommonCalendar cc: calendar)
		{
			CommonCalendarWithNum ccn = new CommonCalendarWithNum();
			ccn.setCdate(cc.getCdate());
			ccn.setCid(cc.getCid());
			ccn.setHoliday(cc.getHoliday());
			ccn.setWeek(cc.getWeek());
			ccn.setNum(courseCalendarDaoImpl.getCourseNum(cc.getCid(), cid));
			ccnList.add(ccn);
		}
		
		json.accumulate("calendar", ccnList);
		return json;
	}
	
	public JSONObject getCourse(String uid, long cid) {
		JSONObject json = new JSONObject();
		List<CourseCalendar> courseList = courseCalendarDaoImpl.getCourseCalendars(cid, uid);
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
		List<CoachService> css = coachServiceDaoImpl.getCoachServiceList(uid);
		json.accumulate("service", css);
		return json;
	}

	//加时间判断
	public JSONObject addCourseCalendar(String cid, long cdate, int maxlimit, long sid, int stype, long tid, String intro){
		JSONObject json = new JSONObject();
		CoachService service  = coachServiceDaoImpl.getCoachService(sid);
		if (service == null || !service.getCid().equals(cid))
			throw new BaseException(ExceptionIdUtil.ServiceNotExsits);
		
		int minutes = 0;
		if (stype == 0){
			minutes = service.getOnline_times();
		}else{
			minutes = service.getOffline_times();
		}
		
		if (minutes == 0)
			throw new BaseException("授课市场不可为0！");
			
		CourseCalendar preCalendar = courseCalendarDaoImpl.getPreviousCourseCalendar(cid, cdate);
		if (preCalendar != null && preCalendar.getCdate()+preCalendar.getMinutes()>cdate)
			throw new BaseException(ExceptionIdUtil.OutOfTimeLimit);
		
		CourseCalendar nextCalendar = courseCalendarDaoImpl.getNextCourseCalendar(cid, cdate);
		if (nextCalendar!=null && cdate+minutes>nextCalendar.getCdate())
			throw new BaseException(ExceptionIdUtil.OutOfTimeLimit);
		
		CommonCalendar cc = commonCalendarDaoImpl.getCommonCalendarByDate((int)(cdate/10000));
		if(cc == null || cc.getCid() != tid)
		{
			throw new BaseException(ExceptionIdUtil.OutOfTimeLimit);
		}
		
		CourseCalendar calendar = new CourseCalendar();
		calendar.setCid(cid);
		calendar.setCdate(cdate);
		calendar.setMaxlimit(maxlimit);
		calendar.setMinutes(minutes);
		calendar.setPublished(0);
		calendar.setSid(sid);
		calendar.setSign(0);
		calendar.setStype(stype);
		calendar.setIntro(intro);
		calendar.setTid(tid);
		long id = courseCalendarDaoImpl.insertCourseCalendar(calendar);
		json = getCourse(cid, tid);
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
	
	public JSONObject removeCourseCalendar(long id){
		JSONObject json = new JSONObject();
		CourseCalendar cc = courseCalendarDaoImpl.getCourseCalendar(id);
		if(cc == null)
			throw new BaseException(ExceptionIdUtil.RecordNotExsits);
		
		if(cc.getSign() > 0)
			throw new BaseException(ExceptionIdUtil.CannotRemoveWithMember);
		
		courseCalendarDaoImpl.deleteCourseCalendar(id);
		json = getCourse(cc.getCid(), cc.getTid());
		return json;
	}
	
	public JSONObject getCourseMember(String uid, long cid){
		JSONObject json = new JSONObject();
		List<CourseMember> members = courseMemberDaoImpl.getCourseMemberList(cid);
		List<UserInfo> users = new ArrayList<UserInfo>();
		List<PersonalCoach> coachList = null;
		coachList = personalCoachDaoImpl.getPersonalCoachs(uid);
		
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
	public JSONObject assignAndUnassignCourseMember(String myid, long id, String[] assign, String[] unassign){
		JSONObject json = new JSONObject();
		CourseCalendar calendar = courseCalendarDaoImpl.getCourseCalendar(id);
		int deep = assign.length-unassign.length;
		if(deep >0 && calendar.getMaxlimit()-calendar.getSign()<deep)
			throw new BaseException(ExceptionIdUtil.OutofMaxLimit);
		
		for(int i=0; i<unassign.length; i++){
			String uid = unassign[i];
			if(!uid.equals(""))
				unassignCalendarMember(id, uid, calendar.getStype());
		}
		
		for(int i=0; i<assign.length; i++){
			String uid = assign[i];
			if(!uid.equals(""))
				assignCalendarMember(id, uid, calendar.getStype());
		}
		
		calendar = courseCalendarDaoImpl.getCourseCalendar(id);
		
		return getCourseMember(myid, id);
	}
	
	//需加事物
	private void unassignCalendarMember(long id, String uid, int type){
		CourseMember member = new CourseMember();
		member.setCid(id);
		member.setUid(uid);
		if(courseMemberDaoImpl.getCourseMember(member)==null)
		{
			throw new BaseException(ExceptionIdUtil.NoCourse);
		}
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
		if(courseMemberDaoImpl.getCourseMember(member)!=null)
		{
			throw new BaseException(ExceptionIdUtil.AsignCourse);
		}
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

	public JSONObject getUserCourseCalendar(String uid) {
		JSONObject json = new JSONObject();
		List<CourseCalendar> courses = courseMemberDaoImpl.getCourseMembers(uid);
		json.accumulate("course", courses);
		return json;
	}
}
