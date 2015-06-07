/**
 * 
 */
package fitfame.po;

import java.io.Serializable;

/**
 * @author zhangshu
 *
 */
public class PersonAndPlan implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6119285411438774809L;

	private PersonalPlan person;
	
	private CoachPlanTemplate plan;

	public PersonalPlan getPerson() {
		return person;
	}

	public void setPerson(PersonalPlan person) {
		this.person = person;
	}

	public CoachPlanTemplate getPlan() {
		return plan;
	}

	public void setPlan(CoachPlanTemplate plan) {
		this.plan = plan;
	}
}
