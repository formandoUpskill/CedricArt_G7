package domain;



import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Artwork {
    private String id;

    private String title;

    private String thumbnail;

    private String date;

    private Partner partner;

    private Artist artist;


    public void setGeneList(List<Gene> geneList) {
        this.geneList = geneList;
    }

    private List<Gene> geneList;

    private OffsetDateTime created_at;
    private  OffsetDateTime updated_at;

    private  String url;

    @SerializedName("_links")
    private Links links;



    public Artwork(){
        this.geneList= new ArrayList<>();
    }

    public void addGene(Gene gene) {
        if(this.geneList == null) {
            this.geneList = new ArrayList<>();
        }

        this.geneList.add(gene);
    }

    public List<Gene> getGeneList() {
        return geneList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OffsetDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(OffsetDateTime created_at) {
        this.created_at = created_at;
    }

    public OffsetDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(OffsetDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }


    public String getPartnerLink() {
        if(links.partner==null)
            return null;
        return links.partner.href;
    }

    // Inner class representing the "_links" part of the JSON
    public static class Links {
        private Thumbnail thumbnail;

        private Genes genes;

        private Partner partner;


        public static class Partner {
            private String href;

            // Getter and setter
        }


        // Inner class for "genes" link
        public static class Genes {
            private String href;

            // Getter and setter
        }


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

    public String getThumbnailLinks() {
        if (links.thumbnail== null)
            return null;
        return links.thumbnail.href;
    }

    public String getGenesLink() {
        return links.genes.href;
    }


    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", date='" + date + '\'' +
                ", partner=" + partner +
                ", artist=" + artist +
                ", geneList=" + geneList +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", url='" + url + '\'' +
                ", links=" + links +
                '}';
    }
}
