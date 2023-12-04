package domain;

import com.google.gson.annotations.SerializedName;

public class Gene {

    private String id;

    private String name;

    private String description;


    @SerializedName("_links")
    private Links links;

    public String getId() {
       return id;
     }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description  = description;

    }

    public void setName(String name) {
        this.name = name;
    }


    public String getArtistsLink() {
        return links.artists.href;
    }


    public String getArtworksLink() {
        return links.artworks.href;
    }

    // Inner class representing the "_links" part of the JSON
    public static class Links {
        private Thumbnail thumbnail;
        private Image image;
        private Self self;
        private Permalink permalink;
        private Artworks artworks;
        private PublishedArtworks publishedArtworks;
        private Artists artists;

        // Inner class for "thumbnail" link
        public static class Thumbnail {
            private String href;

            // Getter and setter

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        // Inner class for "image" link
        public static class Image {
            private String href;
            private boolean templated;

            // Getter and setter

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            public boolean isTemplated() {
                return templated;
            }

            public void setTemplated(boolean templated) {
                this.templated = templated;
            }
        }

        // Inner class for "self" link
        public static class Self {
            private String href;

            // Getter and setter


            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        // Inner class for "permalink" link
        public static class Permalink {
            private String href;

            // Getter and setter


            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        // Inner class for "artworks" link
        public static class Artworks {
            private String href;

            // Getter and setter

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        // Inner class for "published_artworks" link
        public static class PublishedArtworks {
            private String href;

            // Getter and setter


            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        // Inner class for "artists" link
        public static class Artists {
            private String href;

            // Getter and setter


            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        // Getters and setters for all links




        public Thumbnail getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Thumbnail thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Self getSelf() {
            return self;
        }

        public void setSelf(Self self) {
            this.self = self;
        }

        public Permalink getPermalink() {
            return permalink;
        }

        public void setPermalink(Permalink permalink) {
            this.permalink = permalink;
        }

        public Artworks getArtworks() {
            return artworks;
        }

        public void setArtworks(Artworks artworks) {
            this.artworks = artworks;
        }

        public PublishedArtworks getPublishedArtworks() {
            return publishedArtworks;
        }

        public void setPublishedArtworks(PublishedArtworks publishedArtworks) {
            this.publishedArtworks = publishedArtworks;
        }

        public Artists getArtists() {
            return artists;
        }

        public void setArtists(Artists artists) {
            this.artists = artists;
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", links=" + links +
                '}';
    }
}

