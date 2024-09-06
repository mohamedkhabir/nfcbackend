package com.camelsoft.scano.client.models.UserTools;


import com.camelsoft.scano.client.Enum.states;
import com.camelsoft.scano.client.models.Auth.Tools.card_packages;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.tools.Enum.Auth.paymentstate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nfc_card")
public class nfc_card  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id" )
    private Long id;
    @Column(name = "uid")
    private String uid;
    @Column(name = "state")
    private states state = states.REQUESTED;
    @Column(name = "scans")
    private Double scans = 0.0;
    @Column(name = "amount")
    private Double amount = 0.0;
    @Column(name = "paiementstate")
    private paymentstate walletpaiementstate = paymentstate.UNPAID;
    @Column(name = "recievedate")
    private Date recievedate;
    @Column(name = "stripeid")
    private String stripeid;
    @Column(name = "colors")
    private String color;
    @Column(name = "style")
    private String style;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File_model qrImage;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_package")
    private card_packages card_package;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private users user;
    @Column(name = "timestmp")
    private Date timestmp;

    public nfc_card() {
        this.timestmp = new Date();
    }

    public nfc_card(Double amount, String stripeid, String color, String style,paymentstate walletpaiementstate,states state) {
        this.amount = amount;
        this.stripeid = stripeid;
        this.color = color;
        this.style = style;
        this.walletpaiementstate = walletpaiementstate;
        this.state = state;
        this.timestmp = new Date();

    }

    public nfc_card(String uid, states state) {
        this.uid = uid;
        this.state = state;
        this.scans = 0D;
        this.timestmp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public states getState() {
        return state;
    }

    public void setState(states state) {
        this.state = state;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public File_model getQrImage() {
        return qrImage;
    }

    public void setQrImage(File_model qrImage) {
        this.qrImage = qrImage;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }

    public Double getScans() {
        return scans;
    }

    public void setScans(Double scans) {
        this.scans = scans;
    }

    public void IncScans() {
        this.scans +=1;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public paymentstate getWalletpaiementstate() {
        return walletpaiementstate;
    }

    public void setWalletpaiementstate(paymentstate walletpaiementstate) {
        this.walletpaiementstate = walletpaiementstate;
    }

    public Date getRecievedate() {
        return recievedate;
    }

    public void setRecievedate(Date recievedate) {
        this.recievedate = recievedate;
    }

    public String getStripeid() {
        return stripeid;
    }

    public void setStripeid(String stripeid) {
        this.stripeid = stripeid;
    }

    public card_packages getCard_package() {
        return card_package;
    }

    public void setCard_package(card_packages card_package) {
        this.card_package = card_package;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }
}
