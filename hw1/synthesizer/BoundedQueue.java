package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    /** Return size of the buffer. */
    int capacity();
    /** Return number of items currently in the buffer. */
    int fillCount();
    /** Add item x to the end. */
    void enqueue(T x);
    /** Delete and return item from the front. */
    T dequeue();
    /** Return (but do not delete) item from the front. */
    T peek();
    /** Return true if the buffer is empty. */
    default boolean isEmpty(){
        return (fillCount() == 0);
    }
    /** Return true if the buffer is full. */
    default boolean isFull(){
        return (fillCount() == capacity());
    }
    /** Return an iterator object. */
    Iterator<T> iterator();
}
