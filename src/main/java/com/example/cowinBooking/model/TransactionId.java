package com.example.cowinBooking.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TransactionId {
    @NotNull
    String txnId;
}
