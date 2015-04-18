package fitfame.dao;

import fitfame.po.FavorTopic;

public interface IFavorTopicDao {

	public FavorTopic getFavorTopic(FavorTopic info);
	
	public int insertFavorTopic(FavorTopic info);
}
