package com.camelsoft.scano.client.models.notification;

import com.camelsoft.scano.client.Enum.Action;
import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.models.Auth.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "notificationmodel")
public class notificationmodel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "notificatoin_id")
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private users sender;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private users reciver;
    @Column(name = "action")
    private Action action = Action.IDLE;
    @Column(name = "subject")
    private String subject ;
    @Column(name = "content")
    private String content ;
    @Column(name = "status")
    private states status = states.WAITING;
    @Column(name = "timestmp")
    private Date timestmp;

    public notificationmodel() {
        this.timestmp=new Date();
    }

    public notificationmodel(users sender, users reciver, Action action) {
        this.sender = sender;
        this.reciver = reciver;
        this.action = action;
        this.timestmp = new Date();
    }

    public notificationmodel(users sender, users reciver, String subject , String content) {
        this.sender = sender;
        this.reciver = reciver;
        this.content = content;
        this.subject = subject;
        this.timestmp = new Date();
    }

    public states getStatus() {
        return status;
    }

    public void setStatus(states status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public users getSender() {
        return sender;
    }

    public void setSender(users sender) {
        this.sender = sender;
    }

    public users getReciver() {
        return reciver;
    }

    public void setReciver(users reciver) {
        this.reciver = reciver;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }

    public Map<String,String> toMap(){
        Map<String,String> data = new HashMap<>();
        data.put("id",this.id.toString());
        data.put("sender",this.sender.getId().toString());
        data.put("reciver",this.reciver.getId().toString());
        data.put("status",this.status.name());
        data.put("action", this.action.name());
        data.put("timestmp",this.timestmp.toString());
        return data;
    }

}