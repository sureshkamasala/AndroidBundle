package com.ons.abundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class Cache {

    private Map<String, Long> mKeyRemove = new HashMap<String, Long>();

    private Map<String, MapValue<?>> mMap = new HashMap<>();

    private static final int TIME_OUT = 1000;// 1sec
    private Timer mTimer = null;

    protected Cache() {
        mTimer = new Timer();
        KeyRemove removeTask = new KeyRemove();
        mTimer.schedule(removeTask, 0, TIME_OUT);
    }

    public void close() {
        mTimer.cancel();
    }

    public String set(Object value, long timeOutInSec) {
        String key = KeyGenerator.generateKey();
        mMap.put(key, new MapValue<>(value));
        if (timeOutInSec > 0) {
            mKeyRemove.put(key, timeOutInSec);
        }
        return key;
    }

    protected Object get(String key) {
        return mMap.get(key).get();
    }

    protected <V> V get(String key, Class<V> clazz) {

        return clazz.cast(mMap.get(key).get());
    }

    protected void remove(String key) {
        System.out.println("Removing key" + key);
        if (mMap.containsKey(key)) {
            mMap.remove(key);
        }
        if (mKeyRemove.containsKey(key)) {
            mKeyRemove.remove(key);
        }
    }

    class KeyRemove extends TimerTask {
        ArrayList<String> list = null;

        public KeyRemove() {
            list = new ArrayList<String>();
        }

        @Override
        public void run() {
            mKeyRemove.forEach(new BiConsumer<String, Long>() {
                @Override
                public void accept(String t, Long u) {
                    if (u <= 0) {
                        list.add(t);
                        System.out.println("removed" + t);
                    } else {
                        mKeyRemove.put(t, u - 1);
                    }

                }
            });
            if (list.size() > 0) {
                for (String key : list)
                    remove(key);
                list.clear();
            }
        }
    }

    protected void clearAll() {
        mMap.clear();
        mKeyRemove.clear();
    }

    static class MapValue<T> {

        T t;

        public MapValue(T t) {
            this.t = t;
        }

        T get() {
            return t;
        }

    }
}
