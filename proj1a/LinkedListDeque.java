public class LinkedListDeque<T> {

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private Node sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(T item) {
        Node newItem = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = newItem;
        sentinel.next = newItem;
        size += 1;
    }

    public void addLast(T item) {
        Node newItem = new Node(item, sentinel.prev, sentinel);
        sentinel.prev = newItem;
        newItem.prev.next = newItem;
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node iter = sentinel.next;
        while (iter.item != null) {
            System.out.println(iter.item);
            iter = iter.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return removedItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return removedItem;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node ptr = sentinel;
        while (index >= 0) {
            ptr = ptr.next;
            index -= 1;
        }
        return ptr.item;
    }

    private Node getNodeRecursive(Node first, int index) {
        if (index > size) {
            return null;
        }
        if (index == 0) {
            return first.next;
        }
        return getNodeRecursive(first.next, index - 1);
    }

    public T getRecursive(int index) {
        return getNodeRecursive(sentinel, index).item;
    }
}
