package services;

import java.util.List;

public interface IService <T>{

    public void create(String apiUrl, T element);

    public List<T> getAll (String apiUrl);
}
