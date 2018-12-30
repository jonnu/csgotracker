package com.amazon.jonnu.csgotracker.lib;

import java.util.Map;

public interface BKTreeMap<K ,V> {
    void add(K key, V value);
    void addAll(Map<K, V> values);
}
