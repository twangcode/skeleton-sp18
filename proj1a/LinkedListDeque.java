public class LinkedListDeque<T> {

    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private Node sentinel;

    public LinkedListDeque(){
        size = 0;
        sentinel = new Node(null, null, null);
    }

    public void addFirst (T item) {
        Node first = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = first;
        sentinel.prev = first;
    }

    public void addLast (T item) {}

    public boolean isEmpty() {
        return true;
    }

    public int size() {
        return 0;
    }

    public void printDeque() {}

    public T removeFirst() {
        return null;
    }

    public T removeLast() {
        return null;
    }

    public T get(int index) {
        return null;
    }

    public T getRecursive(int index) {
        return null;
    }
}
