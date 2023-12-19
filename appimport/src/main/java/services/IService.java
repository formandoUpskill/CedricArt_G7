package services;

import java.util.List;

/**
 * Generic interface for service operations on objects of type T.
 *
 * @param <T> the type of objects this service handles.
 */
public interface IService <T>{

    /**
     * Creates a new element of type T in the persistent storage.
     *
     * @param apiUrl The URL of the API where the element is to be created.
     * @param element The element of type T to be created.
     */
    public void create(String apiUrl, T element);


    /**
     * Retrieves all elements of type T from the persistent storage.
     *
     * @param apiUrl The URL of the API from where elements are to be fetched.
     * @return A list of elements of type T.
     */
    public List<T> getAll (String apiUrl);
}
