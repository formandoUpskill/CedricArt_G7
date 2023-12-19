package domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents a partner with various attributes such as id, region, and website.
 * Also maintains lists of associated artworks, artists, and exhibitions.
 */
public class Partner {
    private  String id;
    private String region;
    private String name;
    private String website;


    private List<Artwork> artworks;
    private List<Artist> artists;
    private List<Exhibition> exhibitions;


    /**@// TODO: 04/12/2023
     * falta este campo na tabela -- tratar depois
     */
    private transient String email;
    /**@// TODO: 04/12/2023
     * mudar isto para um ojecto tipo utilizador
     */
    private  int id_gallerist;
    /**@// TODO: 04/12/2023
     * mudar isto para um ojecto tipo utilizador
     */
    private  int id_coordinator;


    private Artwork artwork;


    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getId_gallerist() {
        return id_gallerist;
    }

    public void setId_gallerist(int id_gallerist) {
        this.id_gallerist = id_gallerist;
    }

    public int getId_coordinator() {
        return id_coordinator;
    }

    public void setId_coordinator(int id_coordinator) {
        this.id_coordinator = id_coordinator;
    }

    public List<Artwork> getAllArtworks(){
        return artworks;
    }

    public List<Artist> getAllArtists(){
        return artists;
    }

    public List<Exhibition> getAllExhibitions(){
        return exhibitions;
    }

    @SerializedName("_links")
    private Links links;

    public String getWebsiteLink() {

        return links.website.href;
    }


    /**
     * Inner class representing hyperlinks related to the partner.
     */
    public static class Links {

        private Website website;

        // Inner class for "self" link


        // Inner class for "website" link
        public static class Website {
            private String href;

            // Getter and setter
        }

        // Getters and setters for all links
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id='" + id + '\'' +
                ", region='" + region + '\'' +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", artworks=" + artworks +
                ", artists=" + artists +
                ", exhibitions=" + exhibitions +
                ", email='" + email + '\'' +
                ", id_gallerist=" + id_gallerist +
                ", id_coordinator=" + id_coordinator +
                ", artwork=" + artwork +
                ", links=" + links +
                '}';
    }
}
