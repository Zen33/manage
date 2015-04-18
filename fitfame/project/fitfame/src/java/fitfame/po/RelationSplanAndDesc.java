/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class RelationSplanAndDesc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5887720114933727723L;


	private long id;
	//子计划id
	private long spid = -1;
	
	//子计划详细信息id
	private long did = -1;
	
	//详细信息序号 
	private int rank = -1;

	//锻炼数量
	private int  quantity = -1;
	
	//休息时间
	private int duration = -1;
	
	public long getSpid() {
		return spid;
	}

	public void setSpid(long spid) {
		this.spid = spid;
	}

	public long getDid() {
		return did;
	}

	public void setDid(long did) {
		this.did = did;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
