package com.amazon.jonnu.csgotracker.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntBiFunction;
import javax.annotation.Nullable;

public class BKTreeMapImpl<K, V> implements BKTreeMap<K ,V> {

    private Node<K, V> root;
    private final ToIntBiFunction<? super K, ? super K> distanceFunction;

    public BKTreeMapImpl() {
        this((a, b) -> 1);
    }

    public BKTreeMapImpl(ToIntBiFunction<? super K, ? super K> distanceFunction) {
        this.distanceFunction = distanceFunction;
    }

    public void add(K key, V value) {

        if (root == null) {
            root = new Node<>(key, value);
        }

        Node<K, V> node = root;
        while (!node.getKey().equals(key)) {
            int distance = distance(node.getKey(), key);
            Node<K, V> parent = node;
            node = parent.getChildren(distance);
            if (node == null) {
                node = new Node<>(key, value);
                parent.children.put(distance, node);
                break;
            }
        }
    }

    public void addAll(Map<K, V> values) {
        values.forEach(this::add);
    }

    private int distance(K x, K y) {

        int distance = distanceFunction.applyAsInt(x, y);
        if (distance < 0) {
            throw new IllegalArgumentException("new exception needed.");
        }

        return distance;
    }

    static final class Node<K, V> {

        final K key;
        final V value;
        final Map<Integer, Node<K, V>> children = new HashMap<>();

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Nullable
        public Node<K, V> getChildren(final int distance) {
            return children.get(distance);
        }
    }
}
