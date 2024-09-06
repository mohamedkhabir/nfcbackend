package com.camelsoft.scano.client.Requests.Auth;

public class companyrequest {
    private String email;
    private String phonenumber;
    private String secondphonenumber;
    private String companyname;
    private String address;
    private String city;
    private Long cards;
    private Double amount;


    public companyrequest() {
    }

    public companyrequest(String email, String phonenumber, String secondphonenumber, String companyname,String city,String address, Long cards, Double amount) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.secondphonenumber = secondphonenumber;
        this.companyname = companyname;
        this.cards = cards;
        this.city = city;
        this.address = address;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
