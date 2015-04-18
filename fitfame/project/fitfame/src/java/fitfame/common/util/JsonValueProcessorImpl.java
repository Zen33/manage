/*
 * copywrite 2011 深圳证券信息有限公司
 * 不能修改和删除上面的版权声明
 * 此代码属于北京研究网编写，在未经允许的情况下不得传播复制
 */
package fitfame.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * @comment:todo 功能描述
 * @date 2011-5-24
 * @author treetree
 * @since 1.0
 */
public class JsonValueProcessorImpl implements JsonValueProcessor {
	private String format = "yyyy-MM-dd HH:mm:ss";

	public JsonValueProcessorImpl() {

	}

	public JsonValueProcessorImpl(String format) {
		this.format = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if(null==value)return null;
		if (value instanceof Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if(null==value)return null;
		if (value instanceof Date) {
			String str = new SimpleDateFormat(format).format((Date) value);
			return str;
		}
		return value.toString();
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
