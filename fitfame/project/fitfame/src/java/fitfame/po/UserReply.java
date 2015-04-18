/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 * 
 */
public class UserReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3748967745714651589L;

	private UserInfo user;
	private TopicReply reply;
	private TopicInfo topic;
	private CoachComment coachReply;

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public TopicReply getReply() {
		return reply;
	}

	public void setReply(TopicReply reply) {
		this.reply = reply;
	}

	public TopicInfo getTopic() {
		return topic;
	}

	public void setTopic(TopicInfo topic) {
		this.topic = topic;
	}

	public CoachComment getCoachReply() {
		return coachReply;
	}

	public void setCoachReply(CoachComment coachReply) {
		this.coachReply = coachReply;
	}

}
