package fitfame.po;

import java.io.Serializable;

public class VerifyInfo implements Serializable{
	
	private static final long serialVersionUID = -7698520940862900727L;

	private String phone;
	
	private int verifyCount = -1;
	
	private String checkNumber;
	
	private int pwCount = -1;
	
	private String randomPw;
	
	public void setDefaultValue()
	{
		this.verifyCount = 0;
		this.pwCount = 0;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public int getVerifyCount() {
		return verifyCount;
	}

	public void setVerifyCount(int verifyCount) {
		this.verifyCount = verifyCount;
	}

	public int getPwCount() {
		return pwCount;
	}

	public void setPwCount(int pwCount) {
		this.pwCount = pwCount;
	}

	public String getRandomPw() {
		return randomPw;
	}

	public void setRandomPw(String randomPw) {
		this.randomPw = randomPw;
	}
}
