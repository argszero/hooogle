package org.hooogle.util;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-29
 * Time: 上午9:12
 * To change this template use File | Settings | File Templates.
 */
public class TimeCacheMap<K, V> {
    private final long rotateTime;
    private final RotatingMap<K, V> rotatingMap;
    private long lastRotate;

    public TimeCacheMap(long rotateTime) {
        this.rotateTime = rotateTime;
        this.lastRotate = System.currentTimeMillis();
        rotatingMap = new RotatingMap<K, V>(3);
    }

    public TimeCacheMap(long rotateTime, RotatingMap.ExpiredCallback<K, V> callback) {
        this.rotateTime = rotateTime;
        this.lastRotate = System.currentTimeMillis();
        rotatingMap = new RotatingMap<K, V>(callback);
    }

    public V get(K key) {
        return rotatingMap.get(key);
    }

    public void put(K key, V value) {
        long now = System.currentTimeMillis();
        if (now - lastRotate > rotateTime) {
            rotatingMap.rotate();
            lastRotate = now;
        }
        rotatingMap.put(key, value);
    }
}
