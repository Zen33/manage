/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class SquareAdInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5477377421541796476L;

	private String url;
	
	private String cid;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

}
