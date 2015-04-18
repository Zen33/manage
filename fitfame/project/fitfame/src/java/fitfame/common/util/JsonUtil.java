package fitfame.common.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;


/**
 * Json处理方法类，提供了JAVABEAN、COLLECTION与JSON互转的功能 该方法中没有进行异常捕获，请注意处理
 * @author treetree
 */
public class JsonUtil {

	private static JsonConfig cfg;
	static{
		cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl());
		cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl());
	}

	/**
	 * 把collection转换为JSON字符串
	 * 
	 * @param c
	 * @return
	 */
	public static String collection2Json(Collection c) {
		JSONArray obj = JSONArray.fromObject(c,cfg);
		return obj.toString();
	}
	/**
	 * 将JSON转换成Collection
	 * @param json 字符串
	 * @param collectionClz Collection具体子类的Class
	 * @param valueClz  Collection中存放的对象的Class
	 * @return
	 */
	public static Collection json2Collection(String json, Class collectionClz,
			Class valueClz) {
		JSONArray jsonArray = JSONArray.fromObject(json,cfg);
		return JSONArray.toCollection(jsonArray, valueClz);
	}

	/**
	 * 把javabean转换为JSON字符串
	 * 
	 * @param javabean
	 * @return
	 */
	public static String bean2Json(Serializable javabean) {
		JSONObject obj = JSONObject.fromObject(javabean,cfg);
		System.out.println();
		return JSONUtils.valueToString(obj);
	}
	/**
	 * 把JSON串转化为JAVABEAN输出
	 * 
	 * @param json 符合JSON
	 * @param beanClass 流格式类
	 * @return
	 */
	public static Object json2Bean(String json, Class beanClass) {
		return JSONObject.toBean(JSONObject.fromObject(json,cfg), beanClass);
	}
	
	/**
	 * 将String转换成JSON
	 * @param key
	 * @param value
	 * @return
	 */
	public static String string2json(String key, String value) {
		JSONObject object = new JSONObject();
		object.put(key, value);
		return object.toString();
	}
	/**
	 * 将JSON转换成String
	 * @param json
	 * @param key
	 * @return
	 */
	public static String json2String(String json, String key) {
		JSONObject jsonObject = JSONObject.fromObject(json,cfg);
		return jsonObject.get(key).toString();
	}

	/**
	 * 将数组转换成JSON
	 * @param object 数组对象
	 * @return
	 */
	public static String array2json(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object,cfg);
		return jsonArray.toString();
	}
	/**
	 *  将JSON转换成数组
	 * @param json 字符串
	 * @param valueClz 数组中存放的对象的Class
	 * @return
	 */
	public static Object json2Array(String json, Class valueClz) {
		JSONArray jsonArray = JSONArray.fromObject(json,cfg);
		return JSONArray.toArray(jsonArray, valueClz);
	}

	/**
	 *  将Map转换成JSON
	 * @param object Map对象
	 * @return
	 */
	public static String map2json(Map object) {
		JSONObject jsonObject = JSONObject.fromObject(object,cfg);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成Map
	 * @param json 字符串
	 * @param keyArray Map的key
	 * @param valueClz Map中value的Class
	 * @return
	 */
	public static Map json2Map(Object[] keyArray, String json, Class valueClz) {
		JSONObject jsonObject = JSONObject.fromObject(json,cfg);
		Map classMap = new HashMap();

		for (int i = 0; i < keyArray.length; i++) {
			classMap.put(keyArray[i], valueClz);
		}
		return (Map) JSONObject.toBean(jsonObject, Map.class, classMap);
	}

}
