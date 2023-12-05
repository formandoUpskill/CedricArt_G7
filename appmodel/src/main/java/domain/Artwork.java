package domain;



import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Artwork {
    private String id;

    private String title;

    private String thumbnail;

    private String date;

    private Partner partner;


    public void setGeneList(List<Gene> geneList) {
        this.geneList = geneList;
    }

    private List<Gene> geneList;

    private transient LocalDateTime created_at;
    private transient LocalDateTime updated_at;

    private  String url;


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

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
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

    @Override
    public String toString() {
        return "Artwork{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", date='" + date + '\'' +
                ", partner=" + partner +
                ", geneList=" + geneList +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", url='" + url + '\'' +
                '}';
    }
}
