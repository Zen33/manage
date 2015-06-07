package fitfame.common.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import fitfame.common.exception.BaseException;
import fitfame.common.page.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@comment:处理请求参数
 *@date 2011-10-31
 *@author zs
 *@since 1.0
 */
public class ParseRequestParams {
	
	/**
	 * 由于请求参数格式定义如下样式，需要进行解析
	 * q=[attr1:value1;attr2:value2;att3:value3;...]&
	 * @param requestParam 请求参数
	 */
	public static final Map<String,String> getRequestParam(String requestParam){
		
		//如果缺少查询参数，直接返回空的MAP
		if(StringUtils.isBlank(requestParam)){
			return new HashMap<String,String>();
		}
		
		//删除请求串前后的[]
		if(!(requestParam.startsWith("[") && requestParam.endsWith("]"))){
			throw new BaseException("参数形式错误，缺少【】");
		}
		
		requestParam = requestParam.substring(1, requestParam.length() - 1);
		
		Map<String,String> paramMap = new HashMap<String,String>();
		//根据;分析参数
		String s[] = requestParam.split(";");
		
		for(String ss : s){
			if(!ss.contains(":")){
				throw new BaseException("参数形式错误，缺少:分隔key和value");
			}
			//获取参数名称和参数值
			String key = ss.substring(0,ss.indexOf(":"));
			String value = ss.substring(ss.indexOf(":")+1);
			paramMap.put(key, value);
		}
		return paramMap;
	}
	
	/**
	 * 返回值参数解析
	 * r=[result1;result2;result3; result4;...]
	 * @param responseParam
	 * @return
	 */
	public static final String getResponseParam(String responseParam,Serializable vo){
		//如果返回参数值为空，则直接返回整个VO对象
		if(StringUtils.isBlank(responseParam)){
			return JsonUtil.bean2Json(vo);
		}
		//删除请求串前后的[]
		if(!(responseParam.startsWith("[") && responseParam.endsWith("]"))){
			throw new BaseException("参数形式错误，缺少【】");
		}
		
		JSONObject json  = new JSONObject();
		String result[] =  responseParam.substring(1, responseParam.length() - 1).split(";");
		
		
		for(String s : result){
			String methodName = "get" + s.substring(0,1).toUpperCase() + s.substring(1);
			try {
				//通过反射调用get方法
				Method getMethod = vo.getClass().getMethod(methodName, new Class[]{});
				//获取get返回值
				Object value= getMethod.invoke(vo, new Object[]{});
				json.accumulate(s, value);
			} catch (Exception e) {
				throw new BaseException("返回参数名称错误！");
			} 
		}
		
		return json.toString();
	}
	
	/**
	 * 返回值参数解析
	 * r=[result1;result2;result3; result4;...]
	 * @param responseParam
	 * @return voList生成的JSON串
	 */
	public static final String getResponseParamList(String responseParam,List voList){
		//如果返回参数值为空，则直接返回整个LIST对象
		if(StringUtils.isBlank(responseParam)){
			return JsonUtil.collection2Json(voList);
		}
		//删除请求串前后的[]
		if(!(responseParam.startsWith("[") && responseParam.endsWith("]"))){
			throw new BaseException("参数形式错误，缺少【】");
		}
		
		String result[] =  responseParam.substring(1, responseParam.length() - 1).split(";");
		
		JSONArray jArray = new JSONArray();
		//遍历VO
		for(Object vo : voList){
			JSONObject json  = new JSONObject();
			
			for(String s : result){
				String methodName = "get" + s.substring(0,1).toUpperCase() + s.substring(1);
				try {
					//通过反射调用get方法
					Method getMethod = vo.getClass().getMethod(methodName, new Class[]{});
					//获取get返回值
					Object value= getMethod.invoke(vo, new Object[]{});
					json.accumulate(s, value);
				} catch (Exception e) {
					throw new BaseException("返回参数名称错误！");
				} 
			}
			jArray.add(json);
		}
		
		return jArray.toString();
	}	
	/**
	 * 分页参数
	 * p=[pageNo:int1;pageSize:int2]
	 * @param pageParam
	 * @return
	 */
	public static final PageInfo getPageParam(String pageParam){
		//如果分页参数缺少，则直接返回为空
		if(StringUtils.isBlank(pageParam)){
			return null;
		}
		
		Map<String,String> pageMap = getRequestParam(pageParam);
		
		if(!(pageMap.containsKey("pageSize") && pageMap.containsKey("currentPageNo"))){
			throw new BaseException("分页参数不正确，缺少currentPageNo或是pageSize。");
		}
		try{
			int ps = Integer.parseInt(pageMap.get("pageSize"));
			int pn = Integer.parseInt(pageMap.get("currentPageNo"));
		
			//组装分页信息参数
			PageInfo page = new PageInfo();
			int currentPageNo = pn > 0 ? pn : 0;
			currentPageNo = currentPageNo < PageInfo.FIRST_PAGE_NO ? PageInfo.FIRST_PAGE_NO
					: currentPageNo;
			page.setCurrentPageNo(currentPageNo);
			int pageSize = ps > 0 ? ps
					: PageInfo.DEFAULT_PAGE_SIZE;
			page.setPageSize(pageSize);
			
			return page;
		}catch(Exception e){
			throw new BaseException("分页参数不正确，pageNo或pageSize不是数字！");
		}
	}
	/**
	 * 返回分页信息
	 * 如果对分页信息没有特殊要求，可调用此方法，返回currentPageNo,pageSize两个字段
	 * 如果需要返回更多信息，可调用getResponseParam进行处理，处理方式同返回值
	 * @param page
	 * @return
	 */
	public static final String getPageResponse(PageInfo page){
		//如果参数为空，则返回空串
		if(page == null) return "";
		
		//否则的话只返回currentPageNo,pageSize两个字段
		return getResponseParam("[currentPageNo;pageSize]", page);
	}
}
