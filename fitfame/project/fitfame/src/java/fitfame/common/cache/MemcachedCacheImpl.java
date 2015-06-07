﻿package fitfame.common.cache;

import fitfame.common.cache.client.MemCachedClient;
import java.util.Date;
import java.util.Map;



public class MemcachedCacheImpl implements MemcachedCache {
	
	private MemCachedClient memCachedClient;
	private String cacheName;
	
	public MemcachedCacheImpl() {
	}

	public MemcachedCacheImpl(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	@Override
	public boolean add(String key, Object value) {
		return memCachedClient.add(key, value);
	}

	@Override
	public boolean add(String key, Object value, Integer hashCode) {
		return memCachedClient.add(key, value, hashCode);
	}

	@Override
	public boolean add(String key, Object value, Date expiry) {
		return memCachedClient.add(key, value, expiry);
	}

	@Override
	public boolean add(String key, Object value, Date expiry, Integer hashCode) {
		return memCachedClient.add(key, value, expiry, hashCode);
	}

	@Override
	public long addOrDecr(String key) {
		return memCachedClient.addOrDecr(key);
	}

	@Override
	public long addOrDecr(String key, long inc) {
		return memCachedClient.addOrDecr(key, inc);
	}

	@Override
	public long addOrDecr(String key, long inc, Integer hashCode) {
		return memCachedClient.addOrDecr(key, inc, hashCode);
	}

	@Override
	public long addOrIncr(String key) {
		return memCachedClient.addOrIncr(key);
	}

	@Override
	public long addOrIncr(String key, long inc) {
		return memCachedClient.addOrIncr(key, inc);
	}

	@Override
	public long addOrIncr(String key, long inc, Integer hashCode) {
		return memCachedClient.addOrIncr(key, inc, hashCode);
	}

	@Override
	public long decr(String key) {
		return memCachedClient.decr(key);
	}

	@Override
	public long decr(String key, long inc) {
		return memCachedClient.decr(key, inc);
	}

	@Override
	public long decr(String key, long inc, Integer hashCode) {
		return memCachedClient.decr(key, inc, hashCode);
	}

	@Override
	public boolean delete(String key) {
		return memCachedClient.delete(key);
	}

	@Override
	public boolean delete(String key, Date expiry) {
		return memCachedClient.delete(key, expiry);
	}

	@Override
	public boolean delete(String key, Integer hashCode, Date expiry) {
		return memCachedClient.delete(key, hashCode, expiry);
	}

	@Override
	public boolean flushAll() {
		return memCachedClient.flushAll();
	}

	@Override
	public boolean flushAll(String[] servers) {
		return memCachedClient.flushAll(servers);
	}

	@Override
	public Object get(String key) {
		return memCachedClient.get(key);
	}

	@Override
	public Object get(String key, Integer hashCode) {
		return memCachedClient.get(key, hashCode);
	}

	@Override
	public Object get(String key, Integer hashCode, boolean asString) {
		return memCachedClient.get(key, hashCode, asString);
	}

	@Override
	public long getCounter(String key) {
		return memCachedClient.getCounter(key);
	}

	@Override
	public long getCounter(String key, Integer hashCode) {
		return memCachedClient.getCounter(key, hashCode);
	}

	@Override
	public Map<String, Object> getMulti(String[] keys) {
		return memCachedClient.getMulti(keys);
	}

	@Override
	public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes) {
		return memCachedClient.getMulti(keys, hashCodes);
	}

	@Override
	public Map<String, Object> getMulti(String[] keys, Integer[] hashCodes, boolean asString) {
		return memCachedClient.getMulti(keys, hashCodes, asString);
	}

	@Override
	public Object[] getMultiArray(String[] keys) {
		return memCachedClient.getMultiArray(keys);
	}

	@Override
	public Object[] getMultiArray(String[] keys, Integer[] hashCodes) {
		return memCachedClient.getMultiArray(keys, hashCodes);
	}

	@Override
	public Object[] getMultiArray(String[] keys, Integer[] hashCodes, boolean asString) {
		return memCachedClient.getMultiArray(keys, hashCodes, asString);
	}

	@Override
	public long incr(String key) {
		return memCachedClient.incr(key);
	}

	@Override
	public long incr(String key, long inc) {
		return memCachedClient.incr(key, inc);
	}

	@Override
	public long incr(String key, long inc, Integer hashCode) {
		return memCachedClient.incr(key, inc, hashCode);
	}

	@Override
	public boolean keyExists(String key) {
		return memCachedClient.keyExists(key);
	}

	@Override
	public boolean replace(String key, Object value) {
		return memCachedClient.replace(key, value);
	}

	@Override
	public boolean replace(String key, Object value, Integer hashCode) {
		return memCachedClient.replace(key, value, hashCode);
	}

	@Override
	public boolean replace(String key, Object value, Date expiry) {
		return memCachedClient.replace(key, value, expiry);
	}

	@Override
	public boolean replace(String key, Object value, Date expiry, Integer hashCode) {
		return memCachedClient.replace(key, value, expiry, hashCode);
	}

	@Override
	public boolean set(String key, Object value) {
		return memCachedClient.set(key, value);
	}

	@Override
	public boolean set(String key, Object value, Integer hashCode) {
		return memCachedClient.set(key, value, hashCode);
	}

	@Override
	public boolean set(String key, Object value, Date expiry) {
		return memCachedClient.set(key, value, expiry);
	}

	@Override
	public boolean set(String key, Object value, Date expiry, Integer hashCode) {
		return memCachedClient.set(key, value, expiry, hashCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map stats() {
		return memCachedClient.stats();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map stats(String[] servers) {
		return memCachedClient.stats(servers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statsCacheDump(int slabNumber, int limit) {
		return memCachedClient.statsCacheDump(slabNumber, limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statsCacheDump(String[] servers, int slabNumber, int limit) {
		return memCachedClient.statsCacheDump(servers, slabNumber, limit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statsItems() {
		return memCachedClient.statsItems();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statsItems(String[] servers) {
		return memCachedClient.statsItems(servers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statsSlabs() {
		return memCachedClient.statsSlabs();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map statsSlabs(String[] servers) {
		return memCachedClient.statsSlabs(servers);
	}

	@Override
	public boolean storeCounter(String key, long counter) {
		return memCachedClient.storeCounter(key, counter);
	}

	@Override
	public boolean storeCounter(String key, Long counter) {
		return memCachedClient.storeCounter(key, counter);
	}

	@Override
	public boolean storeCounter(String key, Long counter, Integer hashCode) {
		return memCachedClient.storeCounter(key, counter, hashCode);
	}

	@Override
	public boolean clear() {
		return memCachedClient.flushAll();
	}

	@Override
	public Object put(String key, Object value) {
		return set(key, value);
	}

	@Override
	public Object put(String key, Object value, Date expiry) {
		return set(key, value, expiry);
	}

	@Override
	public Object put(String key, Object value, int TTL) {
		return set(key, value, TTL);
	}

	@Override
	public Object remove(String key) {
		Object object = get(key);
		delete(key);
		return object;
	}

	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
	}

	@Override
	public String getCacheName() {
		return this.cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
}