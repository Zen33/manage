package fitfame.po;

import java.io.Serializable;

public class CoachUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3299660717039678322L;

	private CoachInfo coach;
	private UserInfo user;

	public CoachInfo getCoach() {
		return coach;
	}

	public void setCoach(CoachInfo coach) {
		this.coach = coach;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

}
