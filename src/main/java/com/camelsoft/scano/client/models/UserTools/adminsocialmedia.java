package com.camelsoft.scano.client.models.UserTools;

import com.camelsoft.scano.client.models.File.File_model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
@Entity
@Table(name = "adminsocialmedia")
public class adminsocialmedia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL,  orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File_model image;

    @Column(name = "timestmp")
    private Date timestmp;


    public adminsocialmedia()  {
        timestmp = new Date();
    }

    public adminsocialmedia(String name,File_model image) {
        this.image = image;
        this.name = name;
        timestmp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File_model getImage() {
        return image;
    }

    public void setImage(File_model image) {
        this.image = image;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
