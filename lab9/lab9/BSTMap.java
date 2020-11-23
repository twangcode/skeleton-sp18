package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private Set<K> keyset;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
        keyset = new HashSet<>();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        }
        else if (key.compareTo(p.key) < 0) {
            return getHelper(key, p.left);
        }
        else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }
        else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        }
        else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        }
        else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size += 1;
        if (key != null) {
            keyset.add(key);
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keyset;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (keyset.contains(key)) {
            Node node = removeHelper(key, root);
            size -= 1;
            return node.value;
        }
        return null;
    }

    private Node min(Node node) {
        return null;
    }

    private Node deleteMin(Node node) {
        return null;
    }

    private Node removeHelper(K key, Node node){
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) > 0) {
            node.right = removeHelper(key, node.right);
        }
        else if (key.compareTo(node.key) < 0) {
            node.left = removeHelper(key, node.left);
        }
        else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }
            Node newNode = node;
            node = min(newNode.right);
            node.right = deleteMin(newNode.right);
            node.left = newNode.left;
        }
        return node;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (get(key).equals(value)) {
            size -= 1;
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keyset.iterator();
    }
}
