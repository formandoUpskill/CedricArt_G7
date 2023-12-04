package domain;

public class Artwork {
    private String id;
    private String title;
    private String thumbnail;


    public Artwork()
    {

    }

    public Artwork(String id, String title, String thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail= thumbnail;

    }

    public Artwork(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
