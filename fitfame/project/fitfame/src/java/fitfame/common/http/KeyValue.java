package fitfame.common.http;

/**
 * url参数键值对类
 * 
 * @author ahmanz
 * 
 */
public class KeyValue {
	private String key;
	private String value;

	public KeyValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
