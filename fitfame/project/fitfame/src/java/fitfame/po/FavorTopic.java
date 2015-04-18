package fitfame.po;

import java.io.Serializable;

public class FavorTopic implements Serializable{

	private static final long serialVersionUID = 6264268161127516045L;

	private long tid;
	
	private String uid;

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
