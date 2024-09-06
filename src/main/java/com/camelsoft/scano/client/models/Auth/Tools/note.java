package com.camelsoft.scano.client.models.Auth.Tools;


import com.camelsoft.scano.client.models.Auth.company;
import com.camelsoft.scano.client.models.Auth.company_request;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "note")
public class note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "note")
    private String note;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private company_request companie;


    @Column(name = "timestmp")
    private Date timestmp;


    public note() {
        this.timestmp = new Date();
    }

    public note(String note, company_request companie) {
        this.note = note;
        this.companie = companie;
        this.timestmp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public company_request getCompanie() {
        return companie;
    }

    public void setCompanie(company_request companie) {
        this.companie = companie;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
