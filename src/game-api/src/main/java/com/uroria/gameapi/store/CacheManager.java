package com.uroria.gameapi.store;

import java.util.LinkedHashMap;

public class CacheManager {

    protected final LinkedHashMap<String, Object> cache;

    public CacheManager() {
        this.cache = new LinkedHashMap<>();
    }

    public void addToCache(final String key, final Object value) {
        this.cache.put(key, value);
    }

    public void removeFromCache(final String key) {
        this.cache.remove(key);
    }

    public void changeValueFromCache(final String key, final Object value) {
        this.cache.replace(key, value);
    }

    public Object getValueFromCache(final String key) {
        return this.cache.getOrDefault(key, null);
    }

    public boolean containsCache(final String key) {
        return this.cache.containsKey(key);
    }

}
