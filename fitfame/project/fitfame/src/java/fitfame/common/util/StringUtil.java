/*
 * copywrite 2011-2012 深圳证券信息有限公司
 * 不能修改和删除上面的版权声明
 * 此代码属于北京技术中心编写，在未经允许的情况下不得传播复制
*/
package fitfame.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang.StringUtils;

/**
 *@comment:字符串处理
 *@date 2011-11-11
 *@author Xiao Junhe
 *@since 1.0
 */
public class StringUtil {

	/**
	 * 在字符串某位置添加字符
	 * @param str
	 * @param offset
	 * @param c
	 * @return
	 */
	public static String insertString(String str,int offset,char c){
		StringBuffer s = new StringBuffer(str); 
		s.insert(offset, c);
		return s.toString();
	}
	/**
	 * 格式化从数据库返回的数字形式
	 * 如果是.123形式的，格式化为：0.123
	 * 如果是-.123形式的，格式化为：-0.123
	 * 其余形式不变
	 * @param str
	 * @return
	 */
	public static String formatNumber(String str){
		if(StringUtils.isBlank(str)) return "";
		if(str.startsWith(".")) return "0" + str;
		if(str.startsWith("-.")) return insertString(str,1,'0');
		return str;
	}
	/**
	 * 根据日期段的起止日期，返回对应的年和季度
	 * @param startdate 起始日期
	 * @param enddate   结束日期
	 * @return
	 */
	public static String getYearSeason(Date startdate,Date enddate) {
		//判断日期间隔
		int timeRange = DateUtil.extract(enddate,Calendar.MONTH) - DateUtil.extract(startdate,Calendar.MONTH) ;
		//取季度值
		int season = 0;//DateUtil.getSeason(enddate);
		//取年度值
		int year = DateUtil.extract(enddate,Calendar.YEAR);
		//判断输入的字符串
		switch(timeRange){
			case 2 : return year + "年第" + season + "季";
			case 5 : return year + "年" + (season < 3 ? "上" : "下") + "半年";
			case 8 : return year + "年前3季";
			case 11: return year + "年";
			default : break;
		}
		return "";
	}
	
	/**
	 * 转换股票代码,例:000002转成000002.sz,600289转成600289.sh
	 * @param stkcode 股票代码 如:000983
	 * @return 转换后的代码
	 */
	public static String transeStkcode(String stkcode) {
		if(stkcode.startsWith("6")){
			return stkcode+".sh";
		}
		return stkcode+".sz";
	}
	
	public static String getMemcachedKey(UriInfo uriInfo){
		//获取GET路径（不包括主机名和端口，包括路径参数）
		String uri = uriInfo.getPath();
		//获取查询参数
		Map<String, List<String>> map = uriInfo.getQueryParameters();
		if(map.containsKey("p")){
			uri += map.get("p");
		}
		if(map.containsKey("q")){
			uri += map.get("q");
		}
		if(map.containsKey("r")){
			uri += map.get("r");
		}
		if(map.containsKey("o")){
			uri += map.get("o");
		}
		return uri;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(formatNumber("-.123"));
		
	}

}
