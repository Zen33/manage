package fitfame.common.util;

import fitfame.po.CourseCalendar;
import fitfame.po.CourseMember;
import fitfame.po.FavorTopic;
import fitfame.po.VerifyInfo;
import fitfame.po.AdminInfo;
import fitfame.po.CoachComment;
import fitfame.po.CoachInfo;
import fitfame.po.CoachPlan;
import fitfame.po.CoachPlanTemplate;
import fitfame.po.CoachService;
import fitfame.po.FavCoach;
import fitfame.po.FriendInfo;
import fitfame.po.PersonalCoach;
import fitfame.po.PersonalPlan;
import fitfame.po.PersonalSubPlan;
import fitfame.po.RelationPlanAndSplan;
import fitfame.po.RelationSplanAndDesc;
import fitfame.po.StateInfo;
import fitfame.po.SubPlan;
import fitfame.po.SubPlanDesc;
import fitfame.po.TopicInfo;
import fitfame.po.TopicReply;
import fitfame.po.UserInfo;
import fitfame.po.UserPassport;

public class SqlErrorUtil {

	public static final String split = " # ";

	public static String FormUserInfoLog(UserInfo info) {
		return info.getCity() + split + info.getSex() + split + info.getUid()
				+ split + info.getBrithday() + split + info.getUsername()
				+ split + info.getIcon() + split + info.getUid();
	}

	public static String FormAdminInfoLog(AdminInfo info) {
		return info.getName() + split + info.getPw() + split + info.getId();
	}

	public static String FormCoachCommentLog(CoachComment info) {
		return info.getCid() + split + info.getId() + split + info.getContent()
				+ split + info.getIfread() + split + info.getUid() + split
				+ info.getCdate();
	}

	public static String FormCoachInfoLog(CoachInfo info) {
		return info.getCid() + split + info.getIntro() + split
				+ info.getCircle() + split + info.getExp() + split
				+ info.getFav();
	}

	public static String FormCourseCalendarLog(CourseCalendar info) {
		return info.getCid() + split + info.getCdate() + split + info.getId()
				+ split + info.getMaxlimit() + split + info.getMinutes()
				+ split + info.getPublished() + split + info.getSid() + split
				+ info.getSign() + split + info.getStype();
	}

	public static String FormCoachPlanLog(CoachPlan info) {
		return info.getName() + split + info.getPid() + split + info.getCid()
				+ split + info.getDuration() + split + info.getIcon();
	}

	public static String FormCoachPlanTemplateLog(CoachPlanTemplate info) {
		return info.getName() + split + info.getPid() + split + info.getCid()
				+ split + info.getDuration() + split + info.getIcon();
	}

	public static String FormCoachServiceLog(CoachService info) {
		return info.getName() + split + info.getCost() + split + info.getCid()
				+ split + info.getIntro() + split + info.getOnline_times() + split
				+ info.getSid() + split + info.getNum() + split + info.getOffline_times();
	}

	public static String FormCourseMemberLog(CourseMember info) {
		return info.getUid() + split + info.getCid();
	}

	public static String FormFavCoachLog(FavCoach info) {
		return info.getCid() + split + info.getUid();
	}

	public static String FormFriendInfoLog(FriendInfo info) {
		return info.getUid1() + split + info.getUid2();
	}

	public static String FormPersonalCoachLog(PersonalCoach info) {
		return info.getCid() + split + info.getUid() + split + info.getSid()
				+ split + info.getEdate() + split + info.getEdate();
	}

	public static String FormPersonalPlanLog(PersonalPlan info) {
		return info.getId() + split + info.getUid() + split + info.getPid()
				+ split + info.getSdate() + split + info.getEdate();
	}

	public static String FormPersonalSubPlanLog(PersonalSubPlan info) {
		return info.getId() + split + info.getRank() + split + info.getPpid()
				+ split + info.getEdate() + split + info.getSdate();
	}

	public static String FormStateInfoLog(StateInfo info) {
		return info.getUid() + split + info.getReply() + split
				+ info.getComment();
	}

	public static String FormSubPlanLog(SubPlan info) {
		return info.getId() + split + info.getName() + split + split
				+ info.getIntro() + split + info.getIcon();
	}

	public static String FormSubPlanDescLog(SubPlanDesc info) {
		return info.getId() + split + info.getCategory() + split
				+ info.getUrl() + split + info.getIntro();
	}

	public static String FormTopicInfoLog(TopicInfo info) {
		return info.getUid() + split + info.getReply() + split
				+ info.getCdate() + split + info.getCity() + split
				+ info.getContent() + split + info.getFav() + split
				+ info.getId() + split + info.getPic1() + split
				+ info.getPic2() + split + info.getPic3() + split
				+ info.getPic4();
	}

	public static String FormTopicReplyLog(TopicReply info) {
		return info.getUid() + split + info.getCdate() + split
				+ info.getContent() + split + info.getId() + split
				+ info.getIfread() + split + info.getId() + split
				+ info.getTotid() + split + info.getTouid();
	}

	public static String FormUserPassportLog(UserPassport info) {
		return info.getUid() + split + info.getTel() + split + info.getPw();
	}

	public static String FormRelationPlanAndSplanLog(RelationPlanAndSplan info) {
		return info.getId() + split + info.getPid() + split + info.getSpid()
				+ split + info.getDuration() + split + info.getRank();
	}

	public static String FormRelationSplanAndDescLog(RelationSplanAndDesc info) {
		return info.getId() + split + info.getDid() + split + info.getSpid()
				+ split + info.getDuration() + split + info.getRank();
	}

	public static String FormVerifyInfo(VerifyInfo info) {
		return info.getPhone() + split + info.getCheckNumber() + split
				+ info.getVerifyCount() + split + info.getPwCount() + split
				+ info.getRandomPw();
	}

	public static String FormFavTopicLog(FavorTopic info) {
		return info.getUid() + split + info.getTid();
	}
}
