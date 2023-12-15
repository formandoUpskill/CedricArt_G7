package artsy;

import domain.Artwork;

import java.util.List;

public interface IArtsy <T>{

    public String getAll(String apiUrl, String xappToken, List<T> element);


}
