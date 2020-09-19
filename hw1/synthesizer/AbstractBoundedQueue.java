package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    /** Return size of the buffer. */
    public int capacity() {
        return capacity;
    }

    /** Return number of items currently in the buffer. */
    public int fillCount() {
        return fillCount;
    }
//    /** Return true if the buffer is empty. */
//    public boolean isEmpty() {
//        return (fillCount == 0);
//    }
//
//    /** Return true if the buffer is full. */
//    public boolean isFull() {
//        return (fillCount == capacity);
//    }
//
//    /** Add item x to the end. */
//    public abstract void enqueue(T x);
//
//    /** Delete and return item from the front. */
//    public abstract T dequeue();
//
//    /** Return (but do not delete) item from the front. */
//    public abstract T peek();
}
