package com.camelsoft.scano.client.models.Auth.Tools;

import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "card_request")
public class CardRequests implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id" )
    private Long id;

    @Column(name = "amount")
    private Double amount=0d;
    @Column(name = "state")
    private states state = states.REQUESTED;
    @Column(name = "stripeid")
    private String stripeid="";
    @Column(name = "color")
    private String color="";
    @Column(name = "style")
    private String style="";
    @Column(name = "payment_state")
    private paymentstate paiementstate = paymentstate.UNPAID;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File_model image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private users user;
    @Column(name = "timestamp")
    private Date timestamp = new Date();

    public CardRequests(Double amount, String stripeid, String color, String style,paymentstate paiementstate,states state) {
        this.amount = amount;
        this.stripeid = stripeid;
        this.color = color;
        this.style = style;
        this.paiementstate = paiementstate;
        this.state = state;
    }

    public CardRequests() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStripeid() {
        return stripeid;
    }

    public void setStripeid(String stripeid) {
        this.stripeid = stripeid;
    }

    public String getColor() {
        return color;
    }

    public states getState() {
        return state;
    }

    public File_model getImage() {
        return image;
    }

    public void setImage(File_model image) {
        this.image = image;
    }

    public void setState(states state) {
        this.state = state;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public paymentstate getPaiementstate() {
        return paiementstate;
    }

    public void setPaiementstate(paymentstate paiementstate) {
        this.paiementstate = paiementstate;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
