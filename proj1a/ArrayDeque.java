public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirstIndex;
    private int nextLastIndex;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirstIndex = 0;
        nextLastIndex = 1;
    }

    public void addFirst(T item) {
        items[nextFirstIndex] = item;
        nextFirstIndex = (nextFirstIndex - 1 + items.length) % items.length;
        size += 1;
        if (nextFirstIndex == nextLastIndex) {
            resize(size * 2);
        }
    }

    public void addLast(T item) {
        items[nextLastIndex] = item;
        nextLastIndex = (nextLastIndex + 1) % items.length;
        size += 1;
        if (nextLastIndex == nextFirstIndex) {
            resize(size * 2);
        }
    }

    private void resize(int itemSize) {
        T[] newItems = (T[]) new Object[itemSize];
        for (int i = 0; i < size; i += 1) {
            newItems[(nextFirstIndex + i + 1) % newItems.length] =
                    items[(nextFirstIndex + i + 1) % items.length];
        }
        nextLastIndex = (nextFirstIndex + size + 1) % newItems.length;
        items = newItems;
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
        for (int i = 0; i < size; i += 1) {
            System.out.println(items[(nextFirstIndex + 1 + i) % items.length]);
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedItem = items[(nextFirstIndex + 1) % items.length];
        nextFirstIndex = (nextFirstIndex + 1) % items.length;
        size -= 1;
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return removedItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedItem = items[(nextLastIndex - 1 + items.length) % items.length];
        nextLastIndex = (nextLastIndex - 1 + items.length) % items.length;
        size -= 1;
        if (items.length >= 16 && size < items.length / 4) {
            resize(items.length / 2);
        }
        return removedItem;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[(nextFirstIndex + index + 1) % items.length];
    }
}
