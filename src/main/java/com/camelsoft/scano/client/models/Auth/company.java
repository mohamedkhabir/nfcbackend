package com.camelsoft.scano.client.models.Auth;

import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
public class company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "companyname")
    private String companyname;

    @Column(name = "cards")
    private Long cards;

    @Column(name = "amount")
    private Double amount = 0.0;

    @Column(name = "phonenumber")
    private String phonenumber;

    @Column(name = "secondphonenumber")
    private String secondphonenumber;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "promocode",unique = true)
    private String promocode;

    @JsonIgnore
    @OneToMany(mappedBy = "companies",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<users> subscribers = new HashSet<>();

    private Long subscriberscount;

    @Column(name = "timestmp")
    private Date timestmp;


    public company() {
        timestmp = new Date();
    }

    public company(String email, String companyname, String phonenumber, String secondphonenumber,Long cards,Double amount, String promocode,String city,String address) {
        this.email = email;
        this.companyname = companyname;
        this.phonenumber = phonenumber;
        this.city = city;
        this.address = address;
        this.secondphonenumber = secondphonenumber;
        this.amount = amount;
        this.cards = cards;
        this.promocode = promocode;
        timestmp = new Date();
    }


    public Long getSubscriberscount() {
        return subscribers.stream().count();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSecondphonenumber() {
        return secondphonenumber;
    }

    public void setSecondphonenumber(String secondphonenumber) {
        this.secondphonenumber = secondphonenumber;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public Set<users> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<users> subscribers) {
        this.subscribers = subscribers;
    }

    public Long getCards() {
        return cards;
    }

    public void setCards(Long cards) {
        this.cards = cards;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void addSubscribers(users subscribers) {
        this.subscribers.add(subscribers);
        subscribers.setCompanies(this);
    }


    public void removeSubscribers(users subscribers) {
        this.subscribers.remove(subscribers);
        subscribers.setCompanies(null);
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
