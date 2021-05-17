package com.example.cowinBooking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CalendarResponseSchema {

    BigInteger center_id;
    String name;
    String name_l;
    String address;
    String address_l;
    String state_name;
    String state_name_l;
    String district_name;
    String district;
    String block_name;
    String block_name_l;
    String pincode;
    BigDecimal lat;
    String from;
    String to;
    String fee_type;
    List<Sessions> sessions;
}

