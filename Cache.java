import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class Cache<V> {

    Map<String, Long> mKeyRemove = new HashMap<String, Long>();
    Map<String, V> mMap = new HashMap<String, V>();
    final int TIME_OUT = 1000;// 1sec
    Timer mTimer = null;

    public Cache() {
        mTimer = new Timer();
        KeyRemove removeTask = new KeyRemove();
        mTimer.schedule(removeTask, 0, TIME_OUT);
    }

    public void close() {
        mTimer.cancel();
    }

    public String set(V value, long timeOutInSec) {
        String key = KeyGenerator.generateKey();
        mMap.put(key, value);
        if (timeOutInSec > 0) {
            mKeyRemove.put(key, timeOutInSec);
        }
        return key;
    }

    public V get(String key) {
        return mMap.get(key);
    }

    public void remove(String key) {
        System.out.println("Removing key" + key);
        if (mMap.containsKey(key)) {
            mMap.remove(key);
        }
        if (mKeyRemove.containsKey(key)) {
            mKeyRemove.remove(key);
        }
    }

    class KeyRemove extends TimerTask {

        @Override
        public void run() {
            ArrayList<String> list = new ArrayList<String>();
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
            for (String key : list)
                remove(key);
        }
    }

    public void clearAll() {
        mMap.clear();
        mKeyRemove.clear();
    }
}
