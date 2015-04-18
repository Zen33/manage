/**
 * 
 */
package fitfame.action;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
			@QueryParam("cid") String cid,
			@QueryParam("month") int month) {
		JSONObject json = new JSONObject();
		try{
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"calendar/course/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(month < 20150400 || !isValidateUid(cid)){
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "calendar/course/");
			    throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.getCourseCalendar(cid, month);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}

		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("/course/add")
	@Produces("text/html;charset=utf-8")
	public String AddCourseCalendar(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("cdate") long cdate,
			@FormParam("sid") long sid, 
			@FormParam("maxlimit") int maxlimit,
			@FormParam("stype") int stype) {
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
			
			json = courseCalendarService.addCourseCalendar(cid, cdate, maxlimit, sid, stype);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}

		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("/course/refresh")
	@Produces("text/html;charset=utf-8")
	public String RefreshCourseCalendar(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("id") long id,
			@FormParam("maxlimit") int maxlimit) {
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

		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("/course/remove")
	@Produces("text/html;charset=utf-8")
	public String RemoveCourseCalendar(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("id") long id) {
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
			
			courseCalendarService.removeCourseCalendar(id);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
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
			@QueryParam("type") int type) {
		JSONObject json = new JSONObject();
		try{
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"calendar/course/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = courseCalendarService.getCourseMember(id, type);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}

		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("/member/operation")
	@Produces("text/html;charset=utf-8")
	public String operateCourseMember(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("id") long id,
			@FormParam("assign") String assign,
			@FormParam("unassign") String unassign) {
		JSONObject json = new JSONObject();
		try{
			String cid = TokenUtil.checkToken(token);
			if (cid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/member/operation");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			String[] strArray1 = assign.split(",");
			String[] strArray2 = unassign.split(",");
			
			for(int i=0; i<strArray1.length; i++){
				if(!isValidateUid(strArray1[i]))
					throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			for(int i=0; i<strArray2.length; i++){
				if(!isValidateUid(strArray2[i]))
					throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = courseCalendarService.assignAndUnassignCourseMember(id, strArray1, strArray2);
			json.accumulate("status", 200);
			
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
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
}
