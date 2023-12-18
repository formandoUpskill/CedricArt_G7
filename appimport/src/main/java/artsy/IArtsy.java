package artsy;

import com.google.gson.JsonObject;
import domain.Artwork;

import java.util.List;

public interface IArtsy <T>{

    public String getAll(String apiUrl, String xappToken, List<T> element) throws ArtsyException;


}
