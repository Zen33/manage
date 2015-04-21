package fitfame.po;

import java.io.Serializable;

public class SubPlanWithId extends SubPlan implements Serializable {

	private static final long serialVersionUID = 5807349276311756847L;

	private long rid;

	public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}
}
