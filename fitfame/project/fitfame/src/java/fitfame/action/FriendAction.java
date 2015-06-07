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
import fitfame.common.page.PageInfo;
import fitfame.common.util.DefaultValue;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.TokenUtil;
import fitfame.service.FriendService;

/**
 * @author zhangshu
 * 
 */
@Controller
@Path("/{version}/restapi/friend")
public class FriendAction {
	@Autowired(required = true)
	private FriendService friendService;

	@GET
	@NoCache
	@Path("/info/")
	@Produces("text/html;charset=utf-8")
	public String QueryFriendInfo(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/friend/info/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (pagesize <= 0 || pageno < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/friend/info/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = friendService.queryMyFriend(myid, page);
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
	@Path("/info/delete")
	@Produces("text/html;charset=utf-8")
	public String RemoveFriend(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/friend/info/delete");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/friend/info/delete");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			friendService.RemoveFriend(myid, uid);
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
	@Path("/info/sum")
	@Produces("text/html;charset=utf-8")
	public String FriendSum(@PathParam("version") String version,
			@QueryParam("token") String token, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/friend/info/delete");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			json = friendService.FriendSum(myid);
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
	@Path("/info/add")
	@Produces("text/html;charset=utf-8")
	public String AddFriend(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/friend/info/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/friend/info/add");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = friendService.AddFriend(myid, uid);
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
}
