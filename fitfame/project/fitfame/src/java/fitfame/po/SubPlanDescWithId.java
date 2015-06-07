package fitfame.po;

import java.io.Serializable;

public class SubPlanDescWithId extends SubPlanDesc implements Serializable {

	private static final long serialVersionUID = -242947034502949251L;

	private long rid;

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}
}
