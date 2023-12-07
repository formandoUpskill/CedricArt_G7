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

    private Partner partner;

    private String status;


    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getEnd_at() {
        return end_at;
    }

    public void setEnd_at(LocalDateTime end_at) {
        this.end_at = end_at;
    }

    public LocalDateTime getStart_at() {
        return start_at;
    }

    public void setStart_at(LocalDateTime start_at) {
        this.start_at = start_at;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_Partner() {
        return id_Partner;
    }

    public void setId_Partner(String id_Partner) {
        this.id_Partner = id_Partner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "id='" + id + '\'' +
                ", end_at=" + end_at +
                ", start_at=" + start_at +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", id_Partner='" + id_Partner + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

