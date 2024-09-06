package com.camelsoft.scano.tools.Enum.Auth;

public enum paymentstate {
    REFUNDED, // If the supplier reject shipment.
    PAID,
    PAID_BY_COMPANY,
    UNPAID,
    FAKE,
    WAITING
}
