package util;

public class GenericLooper {

    /**
     * Generic method to loop over any iterable.
     * @param iterable The iterable to loop over.
     * @param action The action to perform on each element.
     * @param <T> The type of elements in the iterable.
     */
    public static <T> void forEach(Iterable<T> iterable, Action<T> action) {
        for (T item : iterable) {
            action.perform(item);
        }
    }

    /**
     * Functional interface for actions to be performed on each item.
     * @param <T> The type of elements in the iterable.
     */
    @FunctionalInterface
    public interface Action<T> {
        void perform(T item);
    }
}
