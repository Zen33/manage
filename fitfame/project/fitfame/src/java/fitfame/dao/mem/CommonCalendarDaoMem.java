/**
 * 
 */
package fitfame.dao.mem;

import java.util.List;

import org.springframework.stereotype.Repository;

import fitfame.dao.ICommonCalendarDao;
import fitfame.po.CommonCalendar;

/**
 * @author zhangshu
 *
 */
@Repository
public class CommonCalendarDaoMem implements ICommonCalendarDao {

	/* (non-Javadoc)
	 * @see fitfame.dao.ICommonCalendarDao#getMonthCalendar(int)
	 */
	@Override
	public List<CommonCalendar> getMonthCalendar(int firstDay, int endDay) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonCalendar getCommonCalendarByDate(int date) {
		// TODO Auto-generated method stub
		return null;
	}

}