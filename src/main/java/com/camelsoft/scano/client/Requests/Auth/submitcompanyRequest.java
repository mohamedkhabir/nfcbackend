package com.camelsoft.scano.client.Requests.Auth;

public class submitcompanyRequest {
    private String firstname;
    private String phonenumber;
    private String lastname;
    private String message;

    public submitcompanyRequest() {
    }

    public submitcompanyRequest(String firstname, String phonenumber, String lastname, String message) {
        this.firstname = firstname;
        this.phonenumber = phonenumber;
        this.lastname = lastname;
        this.message = message;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
