package com.oligei.auction.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<T> {

    private Map<Integer,T> cache = new ConcurrentHashMap<Integer, T>();

    public T getValue(Object key) {
        return cache.get(key);
    }

    public void addOrUpdateCache(Integer key,T value) {
        cache.put(key, value);
    }

    // 根据 key 来删除缓存中的一条记录
    public void evictCache(Integer key) {
        cache.remove(key);
    }

    // 清空缓存中的所有记录
    public void evictCache() {
        cache.clear();
    }

    public int getSize(){
        return cache.size();
    }

    public Set<Map.Entry<Integer,T>> getCacheEntrySet(){
        return cache.entrySet();
    }
}
