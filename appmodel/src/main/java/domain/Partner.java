package domain;

import com.google.gson.annotations.SerializedName;

public class Partner {
    private  String id;
    private String region;
    private String email;
    private String name;
    private String website;
    private int id_gallerist;
    private int id_coordinator;

    @SerializedName("_links")
    private Links links;

    public String getWebsiteLink() {

        System.out.println("getWebsiteLink " + links.website.href);
        return links.website.href;
    }

    public String getShowsLink() {

        return links.shows.href;
    }

    public String getId() {
        return id;
    }

    public String getRegion() {
        return region;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public int getId_gallerist() {
        return id_gallerist;
    }

    public int getId_coordinator() {
        return id_coordinator;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setId_gallerist(int id_gallerist) {
        this.id_gallerist = id_gallerist;
    }

    public void setId_coordinator(int id_coordinator) {
        this.id_coordinator = id_coordinator;
    }

    // Inner class representing the "_links" part of the JSON
    public static class Links {
        private Self self;
        private Profile profile;
        private Artworks artworks;
        private Artists artists;
        private PublishedArtworks publishedArtworks;
        private PartnerContacts partnerContacts;
        private Shows shows;
        private Permalink permalink;
        private Website website;

        // Inner class for "self" link
        public static class Self {
            private String href;

            // Getter and setter
        }

        // Inner class for "profile" link
        public static class Profile {
            private String href;

            // Getter and setter
        }

        // Inner class for "artworks" link
        public static class Artworks {
            private String href;

            // Getter and setter
        }

        // Inner class for "artists" link
        public static class Artists {
            private String href;

            // Getter and setter
        }

        // Inner class for "published_artworks" link
        public static class PublishedArtworks {
            private String href;

            // Getter and setter
        }

        // Inner class for "partner_contacts" link
        public static class PartnerContacts {
            private String href;

            // Getter and setter
        }

        // Inner class for "shows" link
        public static class Shows {
            private String href;

            // Getter and setter
        }

        // Inner class for "permalink" link
        public static class Permalink {
            private String href;

            // Getter and setter
        }

        // Inner class for "website" link
        public static class Website {
            private String href;

            // Getter and setter
        }

        // Getters and setters for all links
    }
}
