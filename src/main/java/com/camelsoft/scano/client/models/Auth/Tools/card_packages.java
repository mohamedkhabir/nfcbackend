package com.camelsoft.scano.client.models.Auth.Tools;

import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "card_packages")
public class card_packages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_name")
    private String card_name="";

    @Column(name = "cards")
    private Long cards=0l;

    @Column(name = "colors")
    @ElementCollection(targetClass=String.class)
    private Set<String> colors = new HashSet<>();

    @Column(name = "styles")
    @ElementCollection(targetClass=String.class)
    private Set<String> styles = new HashSet<>();
    @Column(name = "price")
    private Double unitprice = 0d;
    @JsonIgnore
    @OneToMany(mappedBy = "card_package", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<nfc_card> linkedcards = new HashSet<>();
    @Column(name = "timestmp")
    private Date timestmp;

    public card_packages() {
        this.timestmp = new Date();
    }

    public card_packages(String card_name, Set<String> colors, Set<String> styles, Double unitprice,Long cards) {
        this.card_name = card_name;
        this.colors = colors;
        this.styles = styles;
        this.unitprice = unitprice;
        this.cards = cards;
        this.timestmp = new Date();
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }



    public void addCard(nfc_card card){
        linkedcards.add(card);
        card.setCard_package(this);
    }
    public void removeCard(nfc_card card){
        linkedcards.remove(card);
        card.setCard_package(null);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }


    public Set<String> getColors() {
        return colors;
    }

    public void setColors(Set<String> colors) {
        this.colors = colors;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public Set<nfc_card> getLinkedcards() {
        return linkedcards;
    }

    public void setLinkedcards(Set<nfc_card> linkedcards) {
        this.linkedcards = linkedcards;
    }

    public Set<String> getStyles() {
        return styles;
    }

    public Long getCards() {
        return cards;
    }

    public void setCards(Long cards) {
        this.cards = cards;
    }

    public void setStyles(Set<String> styles) {
        this.styles = styles;
    }
    public Date getTimestmp() {
        return timestmp;
    }
    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }
}
