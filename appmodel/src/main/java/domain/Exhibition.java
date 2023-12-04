package domain;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class Exhibition {
    private String id;
    private LocalDateTime end_at;
    private LocalDateTime start_at;
    private String thumbnail;
    private String description;
    private String name;
    private String url;
    private String id_Partner;

    private String status;

    public String getId() {
        return id;
    }

    public LocalDateTime getEnd_at() {
        return end_at;
    }

    public LocalDateTime getStart_at() {
        return start_at;
    }

    public String getThumbnail() {
        if (links.thumbnail== null)
            return null;
        return links.thumbnail.href;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getId_Partner() {
        return id_Partner;
    }

    public void setId_Exhibition(String id) {
        this.id = id;
    }

    public void setEnd_at(LocalDateTime end_at) {
        this.end_at = end_at;
    }

    public void setStart_at(LocalDateTime start_at) {
        this.start_at = start_at;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId_Partner(String id_Partner) {
        this.id_Partner = id_Partner;
    }


    @SerializedName("_links")
    private Links links;

    public static class Links {
        private Thumbnail thumbnail;
        private Image image;
        private Self self;
        private Permalink permalink;
        private Partner partner;
        private Artworks artworks;
        private Images images;

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

        // Inner class for "partner" link
        public static class Partner {
            private String href;

            // Getter and setter
        }

        // Inner class for "artworks" link
        public static class Artworks {
            private String href;

            // Getter and setter
        }

        // Inner class for "images" link
        public static class Images {
            private String href;

            // Getter and setter
        }

        // Getters and setters for all links
    }
}
