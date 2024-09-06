package com.camelsoft.scano.client.Requests.Auth;

import com.camelsoft.scano.tools.Enum.Auth.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class signuprequest {
    private String email;
    private String password;
    private String lastname;
    private String firstname;
    private String phonenumber;
    private String address;
    private String building;
    private String unitnumberaddress;
    private String cardemail;
    private String city;

    private String promocode;



    public signuprequest() {
    }

    public signuprequest(String email, String password, String lastname, String firstname, String phonenumber, String address, String building, String unitnumberaddress, String city, String promocode, String cardemail) {
        this.email = email;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phonenumber = phonenumber;
        this.address = address;
        this.building = building;
        this.unitnumberaddress = unitnumberaddress;
        this.city = city;
        this.cardemail = cardemail;
        this.promocode = promocode;
    }

    public String getCardemail() {
        return cardemail;
    }

    public void setCardemail(String cardemail) {
        this.cardemail = cardemail;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnitnumberaddress() {
        return unitnumberaddress;
    }

    public void setUnitnumberaddress(String unitnumberaddress) {
        this.unitnumberaddress = unitnumberaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
