package com.camelsoft.scano.client.Requests.Auth.Tools;

import com.camelsoft.scano.tools.Enum.Auth.paymentstate;

import javax.persistence.Column;
import java.util.Date;

public class cardRequest {
    private Double amount;
    private String stripeid;
    private String color;
    private String style;

    private paymentstate walletpaiementstate = paymentstate.UNPAID;



    public cardRequest() {
    }

    public cardRequest(Double amount, String stripeid, String color, String style,paymentstate paiementstate) {
        this.amount = amount;
        this.stripeid = stripeid;
        this.color = color;
        this.style = style;
        this.walletpaiementstate = walletpaiementstate;
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

    public void setColor(String color) {
        this.color = color;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public paymentstate getWalletpaiementstate() {
        return walletpaiementstate;
    }

    public void setWalletpaiementstate(paymentstate walletpaiementstate) {
        this.walletpaiementstate = walletpaiementstate;
    }
}
