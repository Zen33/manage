/**
 * 
 */
package fitfame.service;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fitfame.common.exception.BaseServiceException;
import fitfame.common.util.ExceptionIdUtil;
import fitfame.common.util.LogUtil;
import fitfame.dao.IAdminInfoDao;
import fitfame.po.AdminInfo;

/**
 * @author zhangshu
 *
 */

@Service
public class AdminService {

	@Autowired(required=true)
	@Qualifier("adminInfoDaoImpl")
	private IAdminInfoDao adminInfoDaoImpl;
	
	public JSONObject AdminLogin(AdminInfo admin){
		JSONObject json = new JSONObject();
		AdminInfo res = adminInfoDaoImpl.getAdminInfo(admin);
		if(res == null)
		{
			LogUtil.WriteLog(ExceptionIdUtil.AdminLoginMessageError,admin.getName());
			throw new BaseServiceException(ExceptionIdUtil.AdminLoginMessageError,admin.getName());
		}
		else
		{
			json.accumulate("id", res.getId());
		}
		return json;
	}
}
