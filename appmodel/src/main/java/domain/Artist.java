package domain;

import com.google.gson.annotations.SerializedName;


/**
 * Represents an artist with various attributes like id, location, biography, etc.
 * This class also includes nested classes for managing associated links.
 */
public class Artist {
    private String id;
    private String location;
    private String hometown;
    private String name;
    private String biography;
    private String slug;
    private String birthday;
    private String deathday;
    private String thumbnail;
    private String url;
    private String nationality;

    private Partner partner;


    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    @SerializedName("_links")
    private Links links;


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getNationality() {
        return nationality;
    }

    public String getHometown() {
        return hometown;
    }

    public String getBiography() {
        return biography;
    }

    public String getSlug() {
        return slug;
    }

    public String getBirthyear() {
        return birthday;
    }

    public String getDeathyear() {
        return deathday;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailLinks() {
        if (links.thumbnail== null)
            return null;
        return links.thumbnail.href;
    }

    public String getUrl() {
        return url;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setBirthyear(String birthyear) {
        this.birthday = birthyear;
    }

    public void setDeathyear(String deathyear) {
        this.deathday = deathyear;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    /**
     * Inner class representing the "_links" part of the JSON.
     */
    public static class Links {
        private Thumbnail thumbnail;

        // Inner class for "self" link


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


        @Override
        public String toString() {
            return "Links{" +
                    "thumbnail=" + thumbnail +
                    '}';
        }
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", hometown='" + hometown + '\'' +
                ", name='" + name + '\'' +
                ", biography='" + biography + '\'' +
                ", slug='" + slug + '\'' +
                ", birthyear='" + birthday + '\'' +
                ", deathyear='" + deathday + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", url='" + url + '\'' +
                ", nationality='" + nationality + '\'' +
                ", partner=" + partner +
                ", links=" + links +
                '}';
    }
}
