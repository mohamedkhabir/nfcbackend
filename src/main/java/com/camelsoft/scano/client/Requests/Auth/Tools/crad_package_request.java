package com.camelsoft.scano.client.Requests.Auth.Tools;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.HashSet;
import java.util.Set;

public class crad_package_request {

    private String card_name;
    private Set<String> colors = new HashSet<>();

    private Set<String> styles = new HashSet<>();
    private Double unitprice;
    private Long cards;


    public crad_package_request() {
    }

    public crad_package_request(String card_name, Set<String> colors, Set<String> styles, Double unitprice, Long cards) {
        this.card_name = card_name;
        this.colors = colors;
        this.styles = styles;
        this.unitprice = unitprice;
        this.cards = cards;
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

    public Set<String> getStyles() {
        return styles;
    }

    public void setStyles(Set<String> styles) {
        this.styles = styles;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public Long getCards() {
        return cards;
    }

    public void setCards(Long cards) {
        this.cards = cards;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }
}
