/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.CommonCalendar;

/**
 * @author zhangshu
 *
 */
public interface ICommonCalendarDao {

	public List<CommonCalendar> getMonthCalendar(int firstDay, int endDay);
	
	public CommonCalendar getCommonCalendarByDate(int date);
}
