package com.camelsoft.scano.client.models.Auth;

import com.camelsoft.scano.client.Enum.requestState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "company_request")
public class company_request implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id" )
    private Long id;
    @Column(name = "lastname" )
    private String lastname;
    @Column(name = "firstname" )
    private String firstname;
    @Column(name = "phone" )
    private String phone;
    @Column(columnDefinition = "TEXT",name = "message" )
    private String message;
    @Column(name = "requeststate" )
    private requestState requeststate = requestState.WAITING;

    @Column(name = "timestmp")
    private Date timestmp;


    public company_request() {
        this.timestmp = new Date();
    }


    public company_request(String lastname, String firstname, String phone, String message) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.phone = phone;
        this.message = message;
        this.timestmp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public requestState getRequeststate() {
        return requeststate;
    }

    public void setRequeststate(requestState requeststate) {
        this.requeststate = requeststate;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
