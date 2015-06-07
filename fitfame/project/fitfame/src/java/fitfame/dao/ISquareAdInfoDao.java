/**
 * 
 */
package fitfame.dao;

import java.util.List;

import fitfame.po.SquareAdInfo;

/**
 * @author zhangshu
 *
 */
public interface ISquareAdInfoDao {
	public List<SquareAdInfo> getSquareAdInfoList();
	
	public int insertSquareAdInfo(SquareAdInfo info);
	
	public int deleteSquareAdInfo(String url);
}
