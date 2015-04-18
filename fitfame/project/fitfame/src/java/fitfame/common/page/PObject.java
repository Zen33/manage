package fitfame.common.page;

import java.io.Serializable;
import java.util.Date;
/**
 * 所有PO的父类，实现了序列号接口
 * @author treetree
 *
 */
public class PObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1241141178908612806L;
	
	private String created_by;  //创建人
	
	private Date created_date;  //创建日期
	
	private String updated_by;  //修改人
	
	private Date updated_date;  //修改时间

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String createdBy) {
		this.created_by = createdBy;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date createdDate) {
		this.created_date = createdDate;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updatedBy) {
		this.updated_by = updatedBy;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updatedDate) {
		this.updated_date = updatedDate;
	}
}
