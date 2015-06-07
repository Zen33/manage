package fitfame.po;

import java.io.Serializable;

/*
 * 用户基本信息
 */

public class UserInfo implements Serializable {

	private static final long serialVersionUID = -4884335724257894536L;

	// 用户id
	private String uid;
	// 性别
	private String sex = "1";
	// 昵称
	private String username = "";
	// 头像
	private String icon;
	// 出生日期
	private int brithday = -1;
	// 身高cm
	private int height = -1;
	// 体重kg
	private int weight = -1;
	// 所在城市
	private String city;
	// 所在区域
	private String dist;
	// 用户类型 0用户1教练
    private int category;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getBrithday() {
		return brithday;
	}

	public void setBrithday(int brithday) {
		this.brithday = brithday;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}
}
