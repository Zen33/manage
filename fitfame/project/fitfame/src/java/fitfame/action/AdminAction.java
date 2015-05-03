/**
 * 
 */
package fitfame.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fitfame.common.util.GenerateSequenceUtil;
import fitfame.common.util.MD5Util;
import fitfame.po.AdminInfo;
import fitfame.service.AdminService;

/**
 * @author zhangshu
 * 
 */
@Controller
@Path("/{version}/admin")
public class AdminAction {
	@Autowired(required = true)
	private AdminService adminService;

	private String getRemoteIpAddr() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	private HttpSession getSession() {
		return getRequest().getSession();
	}

	private HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	private boolean isIPAuth() {
		return true;
	}

	@GET
	@NoCache
	@Path("/login/")
	@Produces("text/html;charset=utf-8")
	public String Login(@PathParam("version") String version,
			@QueryParam("name") String name, @QueryParam("pw") String pw,
			@Context HttpServletRequest request, 
			@QueryParam("callback") String callback) {
		JSONObject json = new JSONObject();

		if (!isIPAuth()) {
			json.put("status", 400);
			json.put("message", "该客户端ip没有获得授权");
		} else {
			try {
				AdminInfo admin = new AdminInfo();
				admin.setName(name);
				admin.setPw(pw);
				json = adminService.AdminLogin(admin);
				json.accumulate("status", 200);
				request.getSession().setAttribute("admin", MD5Util.toMD5Str(json.getString("id")));
			} catch (Exception e) {
				json.put("status", 400);
				json.put("message", e.getMessage());
			}
		}
		if(callback != null)
		{
			return callback + "(" + json.toString() + ")";
		}
		String res = json.toString();
		return res;
	}
}
