package com.camelsoft.scano.client.models.UserTools;

import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "socialmedia")
public class socialmedia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "show")
    private Boolean show=true;

    @Column(name = "url")
    private String url;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "adminsocialmedia_id")
    private adminsocialmedia adminsocialMedia;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private users user;

    @Column(name = "timestmp")
    private Date timestmp;

    public socialmedia(adminsocialmedia adminsocialMedia,String url) {
        this.url = url;
        this.adminsocialMedia = adminsocialMedia;
        timestmp = new Date();

    }

    public socialmedia() {
        timestmp = new Date();
    }


    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public adminsocialmedia getAdminsocialMedia() {
        return adminsocialMedia;
    }

    public void setAdminsocialMedia(adminsocialmedia adminsocialMedia) {
        this.adminsocialMedia = adminsocialMedia;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }


    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
