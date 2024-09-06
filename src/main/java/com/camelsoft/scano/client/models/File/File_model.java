package com.camelsoft.scano.client.models.File;


import com.camelsoft.scano.client.models.Auth.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "File_model")
public class File_model implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id" )
    private Long id;
    @Column(name = "file_name")
    private String name;
    @Column(name = "file_url")
    private String url;
    @Column(name = "file_type")
    private String type;
    @Column(name = "range")
    private Integer range;
    @Column(name = "file_size")
    private long size;

    // used for mapping users files ! important do not delete

    @Column(name = "timestmp")
    private Date timestmp;

    public File_model() {
        this.timestmp= new Date();
    }

    public File_model(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.timestmp= new Date();
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
