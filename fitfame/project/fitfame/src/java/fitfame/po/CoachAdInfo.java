/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class CoachAdInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6979479008358360508L;

	private String cid;
	
	private String url;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
