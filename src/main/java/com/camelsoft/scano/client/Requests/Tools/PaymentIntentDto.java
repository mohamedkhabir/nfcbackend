package com.camelsoft.scano.client.Requests.Tools;

public class PaymentIntentDto {
    public enum Currency{
        USD, EUR, AED;
    }

    private String description;
    private int amount;
    private Currency currency;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
