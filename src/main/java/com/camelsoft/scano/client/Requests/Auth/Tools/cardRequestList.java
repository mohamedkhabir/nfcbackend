package com.camelsoft.scano.client.Requests.Auth.Tools;

import java.util.List;

public class cardRequestList {

    private List<cardRequest> cardRequests;


    public cardRequestList() {
    }

    public cardRequestList(List<cardRequest> cardRequests) {
        this.cardRequests = cardRequests;
    }

    public List<cardRequest> getCardRequests() {
        return cardRequests;
    }

    public void setCardRequests(List<cardRequest> cardRequests) {
        this.cardRequests = cardRequests;
    }
}
