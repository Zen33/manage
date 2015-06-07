/**
 * 
 */
package fitfame.action;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import sun.misc.BASE64Decoder;

import fitfame.common.exception.BaseException;
import fitfame.common.page.PageInfo;
import fitfame.common.util.DefaultValue;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.FileUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.TokenUtil;
import fitfame.po.TopicInfo;
import fitfame.po.TopicReply;
import fitfame.service.ActivityService;

/**
 * @author zhangshu
 * 
 */
@Controller
@Path("/{version}/restapi/activity")
public class ActivityAction {
	@Autowired(required = true)
	private ActivityService activityService;

	@GET
	@NoCache
	@Path("/public_info/")
	@Produces("text/html;charset=utf-8")
	public String QueryPublicInfo(@PathParam("version") String version,
			@QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (pagesize <= 0 || pageno < 1) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/public_info/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = activityService.queryPublicActivityInfo(page);
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
	@Path("/topic/")
	@Produces("text/html;charset=utf-8")
	public String QueryUserTopicInfo(@PathParam("version") String version,
			@QueryParam("uid") String uid,
			@QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (pagesize <= 0 || pageno < 1
					|| !isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/topic/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = activityService.queryOnesActivityInfo(uid, page);
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
	@Path("/reply/")
	@Produces("text/html;charset=utf-8")
	public String QueryTopicReplyInfo(@PathParam("version") String version,
			@QueryParam("tid") long tid, @QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (pagesize <= 0 || pageno < 1
					|| !isValidateTid(tid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/reply/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = activityService.queryTopicReplyInfo(tid, page);
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
	@Path("/reply/unread/")
	@Produces("text/html;charset=utf-8")
	public String QueryUnreadReplyInfo(@PathParam("version") String version,
			@QueryParam("uid") String uid,
			@QueryParam("pagesize") int pagesize,
			@QueryParam("pageno") int pageno, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			if (pagesize <= 0 || pageno < 1
					|| !isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/reply/unread/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			PageInfo page = new PageInfo();
			page.setPageSize(pagesize);
			page.setCurrentPageNo(pageno);
			json = activityService.queryUnreadReplyInfo(uid, page);
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

	@POST
	@NoCache
	@Path("/topic/add")
	@Produces("text/html;charset=utf-8")
	public String AddNewTopic(@PathParam("version") String version,
			@FormParam("token") String token,
			@FormParam("content") String content,
			@FormParam("pic1") String pic1, @FormParam("picType1") String picType1,
			@FormParam("pic2") String pic2, @FormParam("picType2") String picType2,
			@FormParam("pic3") String pic3, @FormParam("picType3") String picType3,
			@FormParam("pic4") String pic4, @FormParam("picType4") String picType4,
			@FormParam("city") String city, 
			@FormParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/topic/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if (!isValidateContent(content) || pic1 == null || pic1.trim().equals("")) {
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			content = new String(content.getBytes("iso8859-1"), "utf-8");
			pic1 = transPicStreamToString(pic1, picType1, uid);
			pic2 = transPicStreamToString(pic2, picType2, uid);
			pic3 = transPicStreamToString(pic3, picType3, uid);
			pic4 = transPicStreamToString(pic4, picType4, uid);
			TopicInfo topic = new TopicInfo();
			topic.setUid(uid);
			topic.setContent(content);
			topic.setPic1(pic1);
			topic.setPic2(pic2);
			topic.setPic3(pic3);
			topic.setPic4(pic4);
			topic.setReply(0);
			topic.setFav(0);
			Date date = new Date();
			topic.setCdate(date.getTime());
			activityService.createNewTopic(topic);
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
	@Path("/reply/add")
	@Produces("text/html;charset=utf-8")
	public String AddNewReply(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("content") String content,
			@QueryParam("totid") long totid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/reply/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if (!isValidateContent(content) || !isValidateTid(totid)) {
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			TopicReply topic = new TopicReply();
			topic.setUid(uid);
			topic.setContent(content);
            topic.setTotid(totid);
			Date date = new Date();
			topic.setCdate(date.getTime());			
			activityService.createNewReply(topic);
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
	@Path("/favor")
	@Produces("text/html;charset=utf-8")
	public String AddNewFav(@PathParam("version") String version,
			@QueryParam("token") String token,
			@QueryParam("tid") long tid, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();
		try {
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue,
						"/favov/add");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			if (!isValidateTid(tid)) {
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}			
			json = activityService.FavorTopic(uid, tid);
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
	
	private String transPicStreamToString(String param, String picType, String uid) {
		String res = "";
		byte[] buffer = null;
		if (param != null) {
			param = param.replace(' ', '+');
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				buffer = decoder.decodeBuffer(param);
			    res = FileUtil.SaveStringAsTopicPic(buffer, picType, uid);
			} catch (IOException e) {
				LogUtil.WriteLog(ExceptionIdUtil.FileLordFail, "");
				throw new BaseException(ExceptionIdUtil.FileLordFail);
			}

		}

		return res;
	}
	
	private boolean isValidateTid(long tid) {
		if(tid > 0 )
		{
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, tid + "");
		return false;
	}
	
	private boolean isValidateUid(String uid) {
		if (uid != null && uid.trim().length() == DefaultValue.uidLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, uid);
		return false;
	}
	
	private boolean isValidateContent(String content) {
		if (content != null
				&& content.trim().length() < DefaultValue.maxTopicLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, content);
		return false;
	}

	public static boolean isIllegalUrl(String url) {
		if (url == null || url.trim().equals("")) {
			return true;
		}

		String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
				+ "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
				+ "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
				+ "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
				+ "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
				+ "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
				+ "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
				+ "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(url);
		return matcher.matches();
	}
}
