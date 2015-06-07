/**
 * 
 */
package fitfame.action;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.sf.json.JSONObject;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fitfame.common.exception.BaseException;
import fitfame.common.util.DefaultValue;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.TokenUtil;
import fitfame.service.CourseCalendarService;

/**
 * @author zhangshu
 *
 */
@Controller
@Path("/{version}/restapi/calendar")
public class CourseCalendarAction {

	@Autowired(required = true)
	private CourseCalendarService courseCalendarService;
	
	@GET
	@NoCache
	@Path("/course/")
	@Produces("text/html;charset=utf-8")
	public String QueryCalendarInfo(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("page") int page, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"calendar/course/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(!isValidateUid(uid)|| page < -10 || page > 10){
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "calendar/course/");
			    throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.getCourseCalendar(uid, page);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/course/detail/")
	@Produces("text/html;charset=utf-8")
	public String QueryCourseInfo(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("cid") long cid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"calendar/course/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(!isValidateUid(uid)|| !isValidateId(cid)){
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "calendar/course/");
			    throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.getCourse(uid, cid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/course/user/")
	@Produces("text/html;charset=utf-8")
	public String QueryUserCalendarInfo(@PathParam("version") String version,
			@QueryParam("token") String token, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"calendar/course/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = courseCalendarService.getUserCourseCalendar(uid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/course/add")
	@Produces("text/html;charset=utf-8")
	public String AddCourseCalendar(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("cdate") long cdate,
			@QueryParam("sid") long sid, 
			@QueryParam("maxlimit") int maxlimit,
			@QueryParam("stype") int stype,
			@QueryParam("intro") String intro,
			@QueryParam("cid") long tid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String cid = TokenUtil.checkToken(token);
			if (cid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/course/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(cdate < 201504000000L || !isValidateUid(cid)){
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "calendar/course/add");
			    throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.addCourseCalendar(cid, cdate, maxlimit, sid, stype, tid, intro);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/course/refresh")
	@Produces("text/html;charset=utf-8")
	public String RefreshCourseCalendar(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id,
			@QueryParam("maxlimit") int maxlimit, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String cid = TokenUtil.checkToken(token);
			if (cid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/course/refresh");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(!isValidateUid(cid)){
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "calendar/course/refresh");
			    throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.refreshCourseCalendar(id, maxlimit);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/course/remove")
	@Produces("text/html;charset=utf-8")
	public String RemoveCourseCalendar(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String cid = TokenUtil.checkToken(token);
			if (cid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/course/remove");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(!isValidateUid(cid)){
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "calendar/course/remove");
			    throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.removeCourseCalendar(id);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/member")
	@Produces("text/html;charset=utf-8")
	public String QueryCourseMember(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"calendar/course/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = courseCalendarService.getCourseMember(uid, id);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/member/operation")
	@Produces("text/html;charset=utf-8")
	public String operateCourseMember(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id,
			@QueryParam("assign") String[] assign,
			@QueryParam("unassign") String[] unassign, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try{
			String cid = TokenUtil.checkToken(token);
			if (cid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/member/operation");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			for(int i=0; i<assign.length; i++){
				if(!assign[i].equals("") && !isValidateUid(assign[i]))
					throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			for(int i=0; i<unassign.length; i++){
				if(!unassign[i].equals("") && !isValidateUid(unassign[i]))
					throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.assignAndUnassignCourseMember(cid, id, assign, unassign);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		return json.toString();
	}
	
	private boolean isValidateUid(String uid) {
		if (uid != null && uid.trim().length() == DefaultValue.uidLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, uid);
		return false;
	}
	
	private boolean isValidateId(long id) {
		if(id > 0)
		{
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, id + "");
		return false;
	}
}
