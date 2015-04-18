package fitfame.common.util;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	/**
	 * 概念板块
	 */
	public static final String KEY_STOCK_CONCEPT_BLOCK="概念板块";
	/**
	 * 	分类
	 */
	public static final String KEY_CATEGORY="category";
	
	/**
	 * 证监会行业分类:证监会行业门类
	 */
	public static final String CSRS_INDUSTRY_TYPE_A = "证监会门类";
	/**
	 * 证监会行业分类:证监会行业次类
	 */
	public static final String CSRS_INDUSTRY_TYPE_B = "证监会次类";
	/**
	 * 证监会行业分类:证监会行业大类
	 */
	public static final String CSRS_INDUSTRY_TYPE_C = "证监会大类";
	/**
	 * 证监会行业分类:证监会行业中类
	 */
	public static final String CSRS_INDUSTRY_TYPE_D = "证监会中类";
	/**
	 * 行业键值:行业类别
	 */
	public static final String KEY_INDUSTRY_TYPE = "industryType";
	/**
	 * 行业键值:行业名称
	 */
	public static final String KEY_INDUSTRY_NAME = "industry";
	/**
	 * 行业键值:行业排名
	 */
	public static final String KEY_INDUSTRY_RANK = "mktValIndRank";
	/**
	 * 行业键值:行业排名变化值
	 */
	public static final String KEY_INDUSTRY_RANK_CHANGE = "mktValIndRankChg";
	/**
	 * 版块键值:版块名称
	 */
	public static final String KEY_BLOCK_NAME = "conceptBlock";
	/**
	 * 版块键值:版块排名
	 */
	public static final String KEY_BLOCK_RANK = "conceptBlockRank";
	/**
	 * 版块键值:版块排名变化值
	 */
	public static final String KEY_BLOCK_RANK_CHANGE = "conceptBlockRankChg";
	/**
	 * 版块键值:全市场
	 */
	public static final String KEY_ALL_MKT = "全市场";
	/**
	 * 配置文件中的前缀:个股市值管理模块相关
	 */
	public static final String PREKEY_FIELD_STOCK_INDUSTRY_CONCEPT = "stockindustryconcept.";
	/**
	 * 配置文件中的前缀:股东市值管理模块相关
	 */
	public static final String PREKEY_FIELD_SHAREHOLDER_INDUSTRY_CONCEPT = "shareholderindustryconcept.";
	/**
	 * 配置文件中的前缀:行业市盈率相关
	 */
	public static final String PREKEY_FIELD_PER_INDUSTRY="perindustry.";

	/**
	 * 配置文件中的前缀:行业板块市值管理模块相关
	 */
	public static final String PREKEY_FIELD_ALLMAKET_INDUSTRY_CONCEPT = "marketindustryconcept.";
	/**
	 * Oauth key 
	 */
	public static final String KEY_OAUTH = "oauth";

	/**
	 * 返回参数的key:分页信息
	 */
	public static final String RETURN_KEY_PAGE = "page";
	/**
	 * 返回参数的key:列表
	 */
	public static final String RETURN_KEY_ITEMS = "items";
	/**
	 * 返回参数的key:返回状态码
	 */
	public static final String RETURN_KEY_STATUS = "status";
	/**
	 * KEY:md5
	 */
	public static final Object KEY_MD5 = "md5";

	/**
	 * KEY:所有公司对应行业列表的MD5	 	
	 */
	public static final String KEY_MD5_All_INDUSTRY = "KEY_MD5_All_INDUSTRY";
	/**
	 * KEY:所有公司对应板块列表的MD5	 
	 */
	public static final String KEY_MD5_All_CONCEPT = "KEY_MD5_All_CONCEPT";
	/**
	 * 公司对应板块列表
	 */
	public static final String KEY_All_STOCK_CONCEPT_MAP = "KEY_All_STOCK_CONCEPT_MAP";
	/**
	 * 公司对应行业列表
	 */
	public static final String KEY_All_STOCK_INDUSTRY_MAP = "KEY_All_STOCK_INDUSTRY_MAP";
	
	/**
	 * 键值:行业代码前缀
	 */
	public static final String KEY_CSRC_INDUSTRY_CODE = "csrc.industry.";
	
	/**
	 * 宏爵搜索时申万行业代码与原行业代码映射MAP
	 */
	public static final Map<String,String> MAP_NYAPC_SWINDUSTRY = new HashMap<String,String>();
	
	static{
		MAP_NYAPC_SWINDUSTRY.put("S0101","S0101");
		MAP_NYAPC_SWINDUSTRY.put("S0101","S0101");
		MAP_NYAPC_SWINDUSTRY.put("S0101","S0101");
		MAP_NYAPC_SWINDUSTRY.put("S0102","S0102");
		MAP_NYAPC_SWINDUSTRY.put("S0103","S0103");
		MAP_NYAPC_SWINDUSTRY.put("S0201","S02");
		MAP_NYAPC_SWINDUSTRY.put("S0202","S02");
		MAP_NYAPC_SWINDUSTRY.put("S0203","S02");
		MAP_NYAPC_SWINDUSTRY.put("S0204","S02");
		MAP_NYAPC_SWINDUSTRY.put("S0301","S03");
		MAP_NYAPC_SWINDUSTRY.put("S0302","S03");
		MAP_NYAPC_SWINDUSTRY.put("S0303","S03");
		MAP_NYAPC_SWINDUSTRY.put("S0304","S03");
		MAP_NYAPC_SWINDUSTRY.put("S0401","S04");
		MAP_NYAPC_SWINDUSTRY.put("S0402","S04");
		MAP_NYAPC_SWINDUSTRY.put("S0501","S05");
		MAP_NYAPC_SWINDUSTRY.put("S0502","S05");
		MAP_NYAPC_SWINDUSTRY.put("S0601","S0601");
		MAP_NYAPC_SWINDUSTRY.put("S0602","S0602");
		MAP_NYAPC_SWINDUSTRY.put("S0603","S0603");
		MAP_NYAPC_SWINDUSTRY.put("S0604","S06");
		MAP_NYAPC_SWINDUSTRY.put("S0701","S0701");
		MAP_NYAPC_SWINDUSTRY.put("S0702","S0701");
		MAP_NYAPC_SWINDUSTRY.put("S0801","S08");
		MAP_NYAPC_SWINDUSTRY.put("S0802","S08");
		MAP_NYAPC_SWINDUSTRY.put("S0803","S08");
		MAP_NYAPC_SWINDUSTRY.put("S0804","S0804");
		MAP_NYAPC_SWINDUSTRY.put("S0805","S0805");
		MAP_NYAPC_SWINDUSTRY.put("S0806","S08");
		MAP_NYAPC_SWINDUSTRY.put("S0807","S08");
		MAP_NYAPC_SWINDUSTRY.put("S0901","S0901");
		MAP_NYAPC_SWINDUSTRY.put("S0902","S09");
		MAP_NYAPC_SWINDUSTRY.put("S0903","S09");
		MAP_NYAPC_SWINDUSTRY.put("S0904","S09");
		MAP_NYAPC_SWINDUSTRY.put("S1001","S10");
		MAP_NYAPC_SWINDUSTRY.put("S1002","S10");
		MAP_NYAPC_SWINDUSTRY.put("S1101","S1101");
		MAP_NYAPC_SWINDUSTRY.put("S1102","S11");
		MAP_NYAPC_SWINDUSTRY.put("S1201","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1202","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1203","S1203");
		MAP_NYAPC_SWINDUSTRY.put("S1204","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1205","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1206","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1207","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1208","S12");
		MAP_NYAPC_SWINDUSTRY.put("S1301","S13");
		MAP_NYAPC_SWINDUSTRY.put("S1302","S13");
		MAP_NYAPC_SWINDUSTRY.put("S1303","S13");
		MAP_NYAPC_SWINDUSTRY.put("S1304","S1304");
		MAP_NYAPC_SWINDUSTRY.put("S1401","S1401");
		MAP_NYAPC_SWINDUSTRY.put("S1402","S14");
		MAP_NYAPC_SWINDUSTRY.put("S1403","S1403");
		MAP_NYAPC_SWINDUSTRY.put("S1404","S1404");
		MAP_NYAPC_SWINDUSTRY.put("S1501","S15");
		MAP_NYAPC_SWINDUSTRY.put("S1502","S15");
		MAP_NYAPC_SWINDUSTRY.put("S1503","S15");
		MAP_NYAPC_SWINDUSTRY.put("S1504","S15");
		MAP_NYAPC_SWINDUSTRY.put("S1505","S15");
		MAP_NYAPC_SWINDUSTRY.put("S1506","S15");
		MAP_NYAPC_SWINDUSTRY.put("S1601","S16");
		MAP_NYAPC_SWINDUSTRY.put("S1602","S1602");
		MAP_NYAPC_SWINDUSTRY.put("S1603","S1603");
		MAP_NYAPC_SWINDUSTRY.put("S1701","S1701");
		MAP_NYAPC_SWINDUSTRY.put("S1702","S1702");
		MAP_NYAPC_SWINDUSTRY.put("S1801","S18");
		MAP_NYAPC_SWINDUSTRY.put("S1802","S18");
		MAP_NYAPC_SWINDUSTRY.put("S1803","S18");
		MAP_NYAPC_SWINDUSTRY.put("S1901","S1901");
		MAP_NYAPC_SWINDUSTRY.put("S1902","S1902");
		MAP_NYAPC_SWINDUSTRY.put("S1903","S1903");
		MAP_NYAPC_SWINDUSTRY.put("S1904","S1904");
		MAP_NYAPC_SWINDUSTRY.put("S2001","S2001");
		MAP_NYAPC_SWINDUSTRY.put("S2002","S20");
		MAP_NYAPC_SWINDUSTRY.put("S2101","S21");
		MAP_NYAPC_SWINDUSTRY.put("S2102","S21");
		MAP_NYAPC_SWINDUSTRY.put("S2103","S21");
		MAP_NYAPC_SWINDUSTRY.put("S2104","S21");
		MAP_NYAPC_SWINDUSTRY.put("S2105","S21");
		MAP_NYAPC_SWINDUSTRY.put("S2106","S21");
		MAP_NYAPC_SWINDUSTRY.put("S2201","S22");
		MAP_NYAPC_SWINDUSTRY.put("S2202","S22");
		MAP_NYAPC_SWINDUSTRY.put("S9901","");
		MAP_NYAPC_SWINDUSTRY.put("S9901","");

		
	}
	
	
	
	
}
