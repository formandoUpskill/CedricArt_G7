package domain;



import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;


public class Artwork {
    private String id;

    private String title;


    @SerializedName("_links")
    private Links links;



    public String getPartnersLink() {
        if(links.partner==null)
            return null;
        return links.partner.href;
    }


    public String getArtistsLink() {
        return links.artists.href;
    }

    public String getGenesLink() {
        return links.genes.href;
    }

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String thumbnail;
    private String url;

    private String date;

    private String category;
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public String getThumbnail() {

        if (links.thumbnail== null)
            return null;
        return links.thumbnail.href;
    }

    public String getUrl() {
        return url;
    }


    public void setId (String id_Artwork) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public void setCreated_at(LocalDateTime date) {
        this.created_at = date;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDate() {
        return date;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", links=" + links +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", thumbnail='" + thumbnail + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                '}';
    }


    // Inner class representing the "_links" part of the JSON
    public static class Links {
        private Thumbnail thumbnail;
        private Image image;
        private Partner partner;
        private Self self;
        private Permalink permalink;
        private Genes genes;
        private Artists artists;
        private SimilarArtworks similarArtworks;
        private CollectionUsers collectionUsers;
        private SaleArtworks saleArtworks;

        // Inner class for "thumbnail" link
        public static class Thumbnail {
            private String href;

            // Getter and setter
        }

        // Inner class for "image" link
        public static class Image {
            private String href;
            private boolean templated;

            // Getter and setter
        }

        // Inner class for "partner" link
        public static class Partner {
            private String href;

            // Getter and setter
        }

        // Inner class for "self" link
        public static class Self {
            private String href;

            // Getter and setter
        }

        // Inner class for "permalink" link
        public static class Permalink {
            private String href;

            // Getter and setter
        }

        // Inner class for "genes" link
        public static class Genes {
            private String href;

            // Getter and setter
        }

        // Inner class for "artists" link
        public static class Artists {
            private String href;

            // Getter and setter
        }

        // Inner class for "similar_artworks" link
        public static class SimilarArtworks {
            private String href;

            // Getter and setter
        }

        // Inner class for "collection_users" link
        public static class CollectionUsers {
            private String href;

            // Getter and setter
        }

        // Inner class for "sale_artworks" link
        public static class SaleArtworks {
            private String href;

            // Getter and setter
        }

        // Getters and setters for all links
    }

}
