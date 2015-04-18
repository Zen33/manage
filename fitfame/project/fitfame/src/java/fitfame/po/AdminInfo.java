package fitfame.po;

import java.io.Serializable;

public class AdminInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5249803380036158740L;

	private int id;
	
	private String name;
	
	private String pw;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
}
