package fitfame.po;

import java.io.Serializable;

public class UserTopic implements Serializable {

	private static final long serialVersionUID = -1376900566442561195L;
	private UserInfo user;
	private TopicInfo topic;

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public TopicInfo getTopic() {
		return topic;
	}

	public void setTopic(TopicInfo topic) {
		this.topic = topic;
	}

}
