package fitfame.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 写入加密token到cookie
	 * 
	 * @param res
	 * @param domain
	 * @param key
	 * @param userid
	 * @param expiredTime
	 */
	public static void writeCookieToken(HttpServletResponse res, String domain,
			String key, String userid, int expiredTime) {
		Cookie cookie = new Cookie(key, encrypt(userid));
		cookie.setMaxAge(expiredTime);
		cookie.setDomain(domain);
		cookie.setPath("/");
		res.addCookie(cookie);
	}
/**
 * 注销cookie中的token
 * @param res
 * @param req
 * @param key
 */
	public static void removeCookieToken(HttpServletResponse res,
			HttpServletRequest req,String domain, String key) {
//		Cookie cookie = getCookie(req, key);
//		if (cookie != null) {
//			cookie.setMaxAge(0);
//			res.addCookie(cookie);
//		}
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		cookie.setDomain(domain);
		cookie.setPath("/");
		res.addCookie(cookie);
	}

	public static Cookie getCookie(HttpServletRequest req, String cname) {
		Cookie c = null;
		Cookie[] cookieArray = req.getCookies();
		if (cookieArray != null) {
			for (int i = 0; i < cookieArray.length; i++) {
				c = cookieArray[i];
				if (cname.equals(c.getName()))
					return c;
			}
		}
		return null;
	}

	/**
	 * 加密(目前未实现)
	 * 
	 * @param s
	 * @return
	 */
	public static String encrypt(String s) {
		return s;
	}
}
