/**
 * 
 */
package fitfame.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;

import fitfame.common.exception.BaseException;
import fitfame.common.util.DefaultValue;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.common.util.TokenUtil;
import fitfame.service.UserService;

/**
 * @author zhangshu
 * 
 */
@Controller
@Path("/{version}/restapi/user")
public class UserAction {

	@Autowired(required = true)
	private UserService userService;
	
	@GET
	@NoCache
	@Path("/verify/")
	@Produces("text/html;charset=utf-8")
	public String Verify(@PathParam("version") String version,
			@QueryParam("tel") String tel,
			@Context HttpServletRequest request)
	{
		JSONObject json = new JSONObject();
		try{
			String ip = request.getRemoteAddr();
			System.out.println(ip);
			if(!isValidatePhone(tel))
			{
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput,"/verify/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			userService.Verify(tel, ip);
			json.accumulate("status", 200);
		}catch(Exception e){
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		
		return json.toString();
	}

	@POST
	@NoCache
	@Path("/register/")
	@Produces("text/html;charset=utf-8")
	public String RegisterUser(@PathParam("version") String version,
			@FormParam("tel") String tel, @FormParam("pw") String pw,
			@FormParam("check") String check) {
		JSONObject json = new JSONObject();
		try {
			if (!isValidatePhone(tel) || !isValidatePw(pw) || !isValidateCheck(check)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/user/register/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = userService.registerUserPassport(tel,pw,check);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}

		return json.toString();
	}

	@POST
	@NoCache
	@Path("/profile/")
	@Produces("text/html;charset=utf-8")
	public String CompleteInfo(@PathParam("version") String version,
			@FormParam("token") String token, @FormParam("sex") String sex,
			@FormParam("username") String username,
			@FormParam("icon") String icon,
			@FormParam("picType") String picType,
			@FormParam("birthday") int brithday,
			@FormParam("height") int height, @FormParam("weight") int weight,
			@FormParam("city") String city, @FormParam("dist") String dist,
			@FormParam("category") int category) {
		JSONObject json = new JSONObject();
		try {
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/user/profile/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			username = new String(username.getBytes("iso8859-1"), "utf-8");
			byte[] buffer = null;
			if (icon != null) {
				icon = icon.replace(' ', '+');
				BASE64Decoder decoder = new BASE64Decoder();
				buffer = decoder.decodeBuffer(icon);
			}
			
			json = userService.CompleteInfo(uid, buffer, sex, username, picType,
					brithday, height, weight, city, dist, category);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@POST
	@NoCache
	@Path("/coach/profile/")
	@Produces("text/html;charset=utf-8")
	public String CoachCompleteInfo(@PathParam("version") String version,
			@FormParam("token") String token, @FormParam("sex") String sex,
			@FormParam("username") String username,
			@FormParam("icon") File icon,
			@FormParam("picType") String picType,
			@FormParam("birthday") int brithday,
			@FormParam("height") int height, @FormParam("weight") int weight,
			@FormParam("city") String city, @FormParam("dist") String dist,
			@FormParam("category") int category, @FormParam("ads") List<File> ads,
			@FormParam("adsType") List<String> adsType, @FormParam("intro") String intro,
			@FormParam("exp") int exp) {
		JSONObject json = new JSONObject();
		try {
			String uid = TokenUtil.checkToken(token);
			if (uid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/user/profile/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}
			
			json = userService.CoachCompleteInfo(uid, icon, sex, username, picType,
					brithday, height, weight, city, dist, category, ads, adsType, intro, exp);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}


	@GET
	@NoCache
	@Path("/login/")
	@Produces("text/html;charset=utf-8")
	public String Login(@PathParam("version") String version,
			@QueryParam("tel") String tel, @QueryParam("pw") String pw) {
		JSONObject json = new JSONObject();
		try {
			if (!isValidatePhone(tel) || !isValidatePw(pw)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/user/login/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = userService.Login(tel, pw);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}

		return json.toString();
	}

	@GET
	@NoCache
	@Path("/profile/")
	@Produces("text/html;charset=utf-8")
	public String visitUserInfo(@PathParam("version") String version,
			@QueryParam("token") String token, @QueryParam("uid") String uid,
			@QueryParam("date") long date, @QueryParam("content") String content,
			@QueryParam("dure") int dure, @QueryParam("mediaUrl") String mediaUrl,
			@QueryParam("type") int type,@QueryParam("mediaType") int mediaType,
			@QueryParam("qid") int qid) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);

			if (!isValidateUid(uid)) {
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, "/user/profile/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}

			json = userService.queryUserInfo(myid, uid, date);
			JSONObject mJson = new JSONObject();
			mJson.accumulate("uid", uid);
			mJson.accumulate("date", date);
			mJson.accumulate("content", content);
			mJson.accumulate("dure", dure);
			mJson.accumulate("mediaUrl", mediaUrl);
			mJson.accumulate("type", type);
			mJson.accumulate("mediaType", mediaType);
			mJson.accumulate("qid", qid);
			json.accumulate("message", mJson);
			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/findPw/")
	@Produces("text/html;charset=utf-8")
	public String FindPw(@PathParam("version") String version,
			@QueryParam("tel") String tel)
	{
		JSONObject json = new JSONObject();
		try{
			if(!isValidatePhone(tel))
			{
				LogUtil.WriteLog(ExceptionIdUtil.IllegalInput,"/findPw/");
				throw new BaseException(ExceptionIdUtil.IllegalInput);
			}
			
			userService.FindPw(tel);
			json.accumulate("status", 200);
		}catch(Exception e){
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		
		return json.toString();
	}
	
	@GET
	@NoCache
	@Path("/change/pw/")
	@Produces("text/html;charset=utf-8")
	public String ChangePw(@PathParam("version") String version,
			@QueryParam("token") String token, 
			@QueryParam("npw") String npw) {
		JSONObject json = new JSONObject();
		try {
			String myid = TokenUtil.checkToken(token);
			if (myid == null) {
				LogUtil.WriteLog(ExceptionIdUtil.TokenOverDue, "/user/profile/");
				throw new BaseException(ExceptionIdUtil.TokenOverDue);
			}


			json = userService.ChangePw(myid,  npw);

			json.accumulate("status", 200);
		} catch (Exception e) {
			json.put("status", 400);
			json.put("message", e.getMessage());
		}
		return json.toString();
	}
	
	private boolean isValidateName(String username) {
		if(username != null && username.trim().length() < 11)
		{
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, username);
		return false;
	}
	
	private boolean isValidateSex(String sex) {
		if(sex != null && (sex.trim().equals("1") || sex.trim().equals("2")))
		{
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, sex);
		return false;
	}

	private boolean isValidateUid(String uid) {
		if (uid != null && uid.trim().length() == DefaultValue.uidLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, uid);
		return false;
	}

	private boolean isValidatePw(String pw) {
		if (pw != null && !pw.trim().equals("")
				&& pw.trim().length() <= DefaultValue.maxPwLength
				&& pw.trim().length() >= DefaultValue.minPwLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, pw);
		return false;
	}

	private boolean isValidatePhone(String tel) {
		if (tel != null && !tel.trim().equals("")
				&& tel.trim().length() == DefaultValue.telLength) {
			return true;
		}
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, tel);
		return false;
	}
	
	private boolean isValidateCheck(String check) {
		if(check != null && !check.trim().equals(""))
			return true;
		LogUtil.WriteLog(ExceptionIdUtil.IllegalInput, check);
		return false;
	}
}
