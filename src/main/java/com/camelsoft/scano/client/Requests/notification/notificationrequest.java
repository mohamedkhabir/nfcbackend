package com.camelsoft.scano.client.Requests.notification;

public class notificationrequest {
    private Note note;
    private String token;

    public notificationrequest() {
    }

    public notificationrequest(Note note, String token) {
        this.note = note;
        this.token = token;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
