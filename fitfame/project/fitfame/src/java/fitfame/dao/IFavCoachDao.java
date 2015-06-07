/**
 * 
 */
package fitfame.dao;

import fitfame.po.FavCoach;

/**
 * @author zhangshu
 *
 */
public interface IFavCoachDao {

	public FavCoach getFavCoach(FavCoach info);
	
	public int insertFavCoach(FavCoach info);
}
