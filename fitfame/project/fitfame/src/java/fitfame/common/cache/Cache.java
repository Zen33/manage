package fitfame.common.cache;

import java.util.Date;


public interface Cache<K,V>
{
	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key,V value);
	
	/**
	 * 保存有有效期的数�?
	 * @param key
	 * @param value
	 * @param 有效�?
	 * @return
	 */
	public V put(K key,V value, Date expiry);
	
	/**
	 * 保存有有效期的数�?
	 * @param key
	 * @param value
	 * @param 数据超时的秒�?
	 * @return
	 */
	public V put(K key,V value, int TTL);
	
	/**
	 * 获取缓存数据
	 * @param key
	 * @return
	 */
	public V get(K key);
	
	/**
	 * 移出缓存数据
	 * @param key
	 * @return
	 */
	public V remove(K key);
	
	/**
	 * 删除�?��缓存内的数据
	 * @return
	 */
	public boolean clear();
}