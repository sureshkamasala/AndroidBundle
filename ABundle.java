package com.ons.abundle;

public class ABundle {
    Cache mCache;

    private static ABundle INSTANCE;

    private ABundle() {
        mCache = new Cache();
    }

    static {
        INSTANCE = new ABundle();
    }

    public static ABundle getInstance() {
        return INSTANCE;
    }

    public <T> String set(T value) {

        return set(value, 0);
    }

    public <T> String set(T value, long timeOutInSec) {

        return mCache.set(value, timeOutInSec);
    }

    public void remove(String key) {

        mCache.remove(key);
    }
    
    public <V> V get(String key, Class<V> clazz) {
        
        return mCache.get(key, clazz);
    }
    
    public Object get(String key) {
        return mCache.get(key);
    }

    public void clearAll(){
         mCache.clearAll();
    }

}
