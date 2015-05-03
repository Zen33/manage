/**
 * 
 */
package fitfame.action;

import java.util.Date;

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
import fitfame.common.page.PageInfo;
import fitfame.common.util.DefaultValue;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.TokenUtil;
import fitfame.po.CoachComment;
import fitfame.service.CoachCommonService;

/**
 * @author zhangshu
 * 
 */
@Controller
@Path("/{version}/restapi")
public class CoachAction {
	@Autowired(required = true)
	private CoachCommonService coachService;

	@GET
	@NoCache
	@Path("/coach/list_info")
	@Produces("text/html;charset=utf-8")
	public String QueryCoachListInfo(@PathParam("version") String version,
			@QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (pagesize <= 0 || pageno < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput,
						"/coach/list_info");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = coachService.queryCoachInfoList(page);
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
	@Path("/coach/info")
	@Produces("text/html;charset=utf-8")
	public String QueryCoachInfo(@PathParam("version") String version,
			 @QueryParam("cid") String cid, 
				@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (!isValidateUid(cid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/info");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = coachService.queryCoachInfo(cid);
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
	@Path("/coach/info/personal")
	@Produces("text/html;charset=utf-8")
	public String QueryPersonalCoachInfo(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/coach/info/personal");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/info/personal");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = coachService.queryPersonalCoachInfo(myid, uid);
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
	@Path("/coach/service")
	@Produces("text/html;charset=utf-8")
	public String QueryCoachService(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("cid") String cid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if(myid != null)
			{
				json = coachService.queryCoachAllService(myid);
			}
			else
			{
				if (!isValidateUid(cid)) {
					LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/service");
					throw new BaseException(ExceptionIdUtil.IllegalInput);
				}

				json = coachService.queryCoachAllService(cid);
			}
			
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
	@Path("/coach/service/add")
	@Produces("text/html;charset=utf-8")
	public String AddCoachService(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("name") String name,
			@QueryParam("intro") String intro,
			@QueryParam("cost") int cost,
			@QueryParam("online_times") int online_times,
			@QueryParam("offline_times") int offline_times,
			@QueryParam("online") int online,
			@QueryParam("offline") int offline, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {

			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/coach/service/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = coachService.AddCoachService(myid, name, intro, cost, online_times, offline_times, online, offline);
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
	@Path("/coach/service/update")
	@Produces("text/html;charset=utf-8")
	public String updateCoachService(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("sid") long sid,
			@QueryParam("intro") String intro,
			@QueryParam("cost") int cost,
			@QueryParam("online_times") int online_times,
			@QueryParam("offline_times") int offline_times,
			@QueryParam("online") int online,
			@QueryParam("offline") int offline, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {

			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/coach/service/update");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = coachService.updateCoachService(myid,sid, intro, cost, online_times, offline_times, online, offline);
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
	@Path("/coach/service/remove")
	@Produces("text/html;charset=utf-8")
	public String removeCoachService(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("sid") long sid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {

			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/coach/service/remove");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = coachService.removeCoachService(myid, sid);
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
	@Path("/coach/fav")
	@Produces("text/html;charset=utf-8")
	public String AddFavCoach(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("cid") String cid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/coach/fav");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(cid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/fav");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = coachService.addFavCoach(myid, cid);
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
	@Path("/coach/reply/add")
	@Produces("text/html;charset=utf-8")
	public String AddCoachReply(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("content") String content,
			@QueryParam("cid") String cid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"coach/reply/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if (!isValidateContent(content) || !isValidateUid(cid)) {
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			CoachComment cc = new CoachComment();
			cc.setUid(uid);
			cc.setContent(content);
			cc.setCid(cid);
			Date date = new Date();
			cc.setCdate(date.getTime());			
			coachService.createNewReply(cc);
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
	@Path("/coach/reply/")
	@Produces("text/html;charset=utf-8")
	public String QueryCoachReplyInfo(@PathParam("version") String version,
			@QueryParam("cid") String cid, @QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (pagesize <= 0 || pageno < 1
					|| !isValidateUid(cid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/reply/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = coachService.queryCoachReplyInfo(cid, page);
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
	@Path("/coach/buy")
	@Produces("text/html;charset=utf-8")
	public String BuyCoachCourse(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("cid") String cid, 
			@QueryParam("sids") long [] sids, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (!isValidateUid(cid) || sids.length < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/buy");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/reply/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = coachService.addPersonalCoachService(uid, cid, sids);
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
	@Path("/coach/buy/verify")
	@Produces("text/html;charset=utf-8")
	public String VerifyCoachCourse(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("cid") String cid, 
			@QueryParam("sids") long [] sids, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (!isValidateUid(cid) || sids.length < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/buy");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/reply/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = coachService.VerifyPersonalCoachService(uid, cid, sids);
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
	@Path("/coach/complete")
	@Produces("text/html;charset=utf-8")
	public String CompleteCoachCourse(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("cid") String cid, 
			@QueryParam("sid") long sid, @QueryParam("online") int online,
			@QueryParam("offline") int offline, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (!isValidateUid(cid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/coach/complete");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/reply/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = coachService.completePersonalCoachService(uid, cid, sid, online, offline);
			json.put("status", 200);

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
	
	@POST
	@NoCache
	@Path("/alipay/")
	@Produces("text/html;charset=utf-8")
	public String VerfiyAlipay(@PathParam("version") String version,
//			@FormParam("notify_time") Date notify_time,
//			@FormParam("notify_type") String notify_type,
			@FormParam("notify_id") String notify_id,  
//			@FormParam("sign_type") String sign_type,
//			@FormParam("sign") String sign,
			@FormParam("out_trade_no") String out_trade_no
//			@FormParam("subject") String subject, @FormParam("payment_type") String payment_type,
//			@FormParam("trade_no") String trade_no, @FormParam("trade_status") String trade_status,
//			@FormParam("seller_id") String seller_id, @FormParam("seller_email") String seller_email,
//			@FormParam("buyer_id") String buyer_id, @FormParam("buyer_email") String buyer_email,
//			@FormParam("total_fee") float total_fee, @FormParam("quantity") float quantity,
//			@FormParam("price") float price, @FormParam("body") String body,
//			@FormParam("gmt_create") Date gmt_create, @FormParam("gmt_payment") Date gmt_payment,
//			@FormParam("is_total_fee_adjust") String is_total_fee_adjust, 
//			@FormParam("use_coupon") String use_coupon, @FormParam("discount") String discount
			)
	{		
//		LogUtil.WriteInfoLog(notify_time.toString());
//		LogUtil.WriteInfoLog(notify_type);
		//LogUtil.WriteInfoLog(notify_id);
//		LogUtil.WriteInfoLog(sign_type);
//		LogUtil.WriteInfoLog(sign);
		//LogUtil.WriteInfoLog(out_trade_no);
//		LogUtil.WriteInfoLog(subject);
//		LogUtil.WriteInfoLog(payment_type);
//		LogUtil.WriteInfoLog(trade_no);
//		LogUtil.WriteInfoLog(trade_status);
//		LogUtil.WriteInfoLog(seller_id);
//		LogUtil.WriteInfoLog(seller_email);
//		LogUtil.WriteInfoLog(buyer_id);
//		LogUtil.WriteInfoLog(buyer_email);
//		LogUtil.WriteInfoLog(total_fee + "");
//		LogUtil.WriteInfoLog(quantity + "");
//		LogUtil.WriteInfoLog(price + "");
//		LogUtil.WriteInfoLog(body);
//		LogUtil.WriteInfoLog(gmt_create.toString());
//		LogUtil.WriteInfoLog(gmt_payment.toString());
//		LogUtil.WriteInfoLog(is_total_fee_adjust);
//		LogUtil.WriteInfoLog(use_coupon);
//		LogUtil.WriteInfoLog(discount);
		
		return "success";
	}
	
	
	private boolean isValidateContent(String content) {
		if (content != null
				&& content.trim().length() < DefaultValue.maxTopicLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, content);
		return false;
	}

	private boolean isValidateUid(String uid) {
		if (uid != null && uid.trim().length() == DefaultValue.uidLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, uid);
		return false;
	}
}
