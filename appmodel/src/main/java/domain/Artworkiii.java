package domain;

public class Artworkiii {
    private String id;
    private String title;
    private String category;
    private String medium;

    public Artworkiii(String id, String title, String category, String medium) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.medium = medium;
    }

    public Artworkiii(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getMedium() {
        return medium;
    }


    @Override
    public String toString() {
        return "Artwork{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
