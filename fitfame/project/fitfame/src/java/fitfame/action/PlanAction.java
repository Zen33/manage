/**
 * 
 */
package fitfame.action;

import java.io.File;

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
import fitfame.service.PlanService;

/**
 * @author zhangshu
 * 
 */
@Controller
@Path("/{version}/restapi/plan")
public class PlanAction {
	@Autowired(required = true)
	private PlanService planService;

	@GET
	@NoCache
	@Path("/user/")
	@Produces("text/html;charset=utf-8")
	public String QueryUserPlan(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/plan/user");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/plan/user");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.queryUserPlan(uid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/user/add/")
	@Produces("text/html;charset=utf-8")
	public String AddUserPlan(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("pid") long  pid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/plan/user");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateId(pid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/plan/user");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.AddUserPlan(myid, pid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/user/replace/")
	@Produces("text/html;charset=utf-8")
	public String ReplaceUserPlan(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("pid") long  pid
			, @QueryParam("uid") String  uid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/plan/user");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateId(pid) || !isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/plan/user");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.ReplaceUserPlan(myid, pid,uid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/user/remove/")
	@Produces("text/html;charset=utf-8")
	public String RemoveUserPlan(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("pid") long  pid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/plan/user");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateId(pid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/plan/user");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.RemoveUserPlan(myid, pid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/coach/user/")
	@Produces("text/html;charset=utf-8")
	public String QueryCoachUser(@PathParam("version") String version,
			@QueryParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			json = planService.queryCoachUser(myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/coach/user/undo/")
	@Produces("text/html;charset=utf-8")
	public String QueryUserSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("uid") String uid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = planService.QueryUserSubPlan(myid,uid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}

	@GET
	@NoCache
	@Path("/user/subplan/undo")
	@Produces("text/html;charset=utf-8")
	public String QueryUndoUserSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.queryUserUndoSubPlan(uid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}

	@GET
	@NoCache
	@Path("/user/subplan/done")
	@Produces("text/html;charset=utf-8")
	public String QueryDoneUserSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid,
			@QueryParam("edate") long edate) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.queryUserDoneSubPlan(uid, edate);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/subplan/step")
	@Produces("text/html;charset=utf-8")
	public String updateSubPlanStep(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("spid") long spid,
			@QueryParam("step") int step) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/plan/user/subplan/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(spid))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.updatePersonalPlanStep(myid,spid, step);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	

	@GET
	@NoCache
	@Path("coach/plandesc")
	@Produces("text/html;charset=utf-8")
	public String QueryAvailableSubPlanDesc(@PathParam("version") String version,
			@QueryParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = planService.queryAllAvailablePlanDesc(myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/subplan")
	@Produces("text/html;charset=utf-8")
	public String QueryAvailableSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = planService.queryAllAvailableSubPlan(myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plantemplate")
	@Produces("text/html;charset=utf-8")
	public String QueryAllPlanTemplate(@PathParam("version") String version,
			@QueryParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = planService.queryAllPlanTemplate(myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/coach/")
	@Produces("text/html;charset=utf-8")
	public String QueryAllPlan(@PathParam("version") String version,
			@QueryParam("token") String token) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = planService.queryAllPlan(myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plan/publish")
	@Produces("text/html;charset=utf-8")
	public String PublishCoachPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("pid") long pid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(pid))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.publishCoachPlan(pid,myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plan/remove")
	@Produces("text/html;charset=utf-8")
	public String RemoveCoachPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("pid") long pid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(pid))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.RemoveCoachPlan(pid,myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("coach/plandesc/add")
	@Produces("text/html;charset=utf-8")
	public String AddNewPlanDesc(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("name") String name, 
			@FormParam("intro")  String intro, 
			@FormParam("pic") File pic,
			@FormParam("picType")  String picType,
			@FormParam("media")  File media,
			@FormParam("mediaType")  String mediaType,
			@FormParam("category") int category, 
			@FormParam("quantity") int quantity, 
			@FormParam("units") String units, 
			@FormParam("duration") int duration,
			@FormParam("share") int share) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			planService.addPlanDesc(myid, name, intro, pic,category, quantity, units, duration, share, pic, picType, media, mediaType);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plandesc/remove")
	@Produces("text/html;charset=utf-8")
	public String RemoveCoachPlanDesc(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.RemoveCoachPlanDesc(id,myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("coach/plandesc/update")
	@Produces("text/html;charset=utf-8")
	public String UpdateNewPlanDesc(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("id") long id, 
			@FormParam("name") String name, 
			@FormParam("intro")  String intro, 
			@FormParam("pic") File pic,
			@FormParam("picType") String picType,
			@FormParam("mediaType") String mediaType,
			@FormParam("media")  File media, 
			@FormParam("category") int category, 
			@FormParam("quantity") int quantity, 
			@FormParam("units") String units, 
			@FormParam("duration") int duration,
			@FormParam("share") int share) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			planService.updatePlanDesc(id, intro, pic, category, quantity, units, duration, share, pic, picType, media, mediaType, myid,name);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/subplan/add")
	@Produces("text/html;charset=utf-8")
	public String AddNewSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("name") String name, 
			@QueryParam("intro")  String intro,
			@QueryParam("duration") int duration) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			planService.addSubPlan(myid, name, intro,duration);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/subplan/update")
	@Produces("text/html;charset=utf-8")
	public String UpdateSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id, 
			@QueryParam("name") String name,
			@QueryParam("intro")  String intro, 
			@QueryParam("duration") int duration) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			planService.updateSubPlan(myid, id, intro, duration, name);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/subplan/delete")
	@Produces("text/html;charset=utf-8")
	public String DeleteSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.deleteSubPlan(myid, id);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plantemplate/add")
	@Produces("text/html;charset=utf-8")
	public String AddNewPlanTemplate(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("name") String name, 
			@QueryParam("intro")  String intro, 
			@QueryParam("icon")  String icon, 
			@QueryParam("duration") int duration) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			planService.addPlanTemplate(myid, name, intro, icon, duration);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plantemplate/update")
	@Produces("text/html;charset=utf-8")
	public String UpdatePlanTemplate(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("name") String name,
			@QueryParam("id") long id, 
			@QueryParam("intro")  String intro, 
			@QueryParam("icon")  String icon, 
			@QueryParam("duration") int duration) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			planService.updatePlanTemplate(myid, id, intro, icon, duration, name);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plantemplate/delete")
	@Produces("text/html;charset=utf-8")
	public String DeletePlanTemplate(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("id") long id) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plandesc");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.deletePlanTemplate(myid, id);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/subplan/desc")
	@Produces("text/html;charset=utf-8")
	public String QuerySubPlanDesc(@PathParam("version") String version,
			 @QueryParam("spid") long spid) {
		JSONObject json = new JSONObject();
		try {
			json = planService.querySubPlanDesc(spid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/subplan/desc/add")
	@Produces("text/html;charset=utf-8")
	public String AssignDescToSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("spid") long pid, 
			@QueryParam("did") long did) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/subplan/desc/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(pid) || !isValidateId(did))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = planService.assignDescToSubPlan(myid, pid, did);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
//	@GET
//	@NoCache
//	@Path("coach/subplan/desc/update")
//	@Produces("text/html;charset=utf-8")
//	public String UpdateDescToSubPlan(@PathParam("version") String version,
//			@QueryParam("token") String token,
//			@QueryParam("id") long id, 
//			@QueryParam("rank") int rank) {
//		JSONObject json = new JSONObject();
//		try {
//			String myid = TokenUtil.checkToken(token);
//			if (myid == null) {
//				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
//						"coach/subplan/desc/update");
//				throw new BaseException(ExceptionIdUtil.TokenOverDue);
//			}
//			if(!isValidateId(id))
//			{
//				throw new BaseException(ExceptionIdUtil.IllegalInput);
//			}
//			
//			planService.updateDescToSubPlan(id, rank, quantity, duration);
//			json.accumulate("status", 200);
//		} catch (Exception e) {
//			json.put("status", 400);
//			json.put("message", e.getMessage());
//		}
//		return json.toString();
//	}
	
	@GET
	@NoCache
	@Path("coach/subplan/desc/remove")
	@Produces("text/html;charset=utf-8")
	public String UnassignDescFromSubPlan(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("rid") long id) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/subplan/desc/remove");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = planService.removeDescFromSubPlan(myid, id);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/subplan/")
	@Produces("text/html;charset=utf-8")
	public String QuerySubPlan(@PathParam("version") String version,
			@QueryParam("id") long id) {
		JSONObject json = new JSONObject();
		try {
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.querySubPlan(id);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("coach/plan/subplan/add")
	@Produces("text/html;charset=utf-8")
	public String AssignSubPlanToPlanTemplate(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("pid") long pid, 
			@QueryParam("spid") long spid, 
			@QueryParam("rank") int rank) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plan/subplan/update");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if(!isValidateId(pid))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			json = planService.assignSubPlanToPlan(pid, spid, rank, myid);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
//	@GET
//	@NoCache
//	@Path("coach/plan/subplan/update")
//	@Produces("text/html;charset=utf-8")
//	public String UpdateSubPlanToPlanTemplate(@PathParam("version") String version,
//			@QueryParam("token") String token,
//			@QueryParam("id") long id, 
//			@QueryParam("rank") int rank, 
//			@QueryParam("duration") int duration) {
//		JSONObject json = new JSONObject();
//		try {
//			String myid = TokenUtil.checkToken(token);
//			if (myid == null) {
//				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
//						"coach/plan/subplan/update");
//				throw new BaseException(ExceptionIdUtil.TokenOverDue);
//			}
//			if(!isValidateId(id))
//			{
//				throw new BaseException(ExceptionIdUtil.IllegalInput);
//			}
//			
//			planService.updateSubPlanToPlan(id, rank, duration);
//			json.accumulate("status", 200);
//		} catch (Exception e) {
//			json.put("status", 400);
//			json.put("message", e.getMessage());
//		}
//		return json.toString();
//	}
	
	@GET
	@NoCache
	@Path("coach/plan/subplan/remove")
	@Produces("text/html;charset=utf-8")
	public String UnassignSubPlanFromPlanTemplate(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("rid") long id) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/plan/subplan/remove");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			if(!isValidateId(id))
			{
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			json = planService.removeSubPlanFromPlan(id, myid);
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

	private boolean isValidateId(long id) {
		if(id > 0)
		{
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, id + "");
		return false;
	}
}
