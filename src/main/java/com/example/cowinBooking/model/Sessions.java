package com.example.cowinBooking.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Sessions {
    UUID session_id;
    String date;
    int available_capacity;
    int min_age_limit;
    String vaccine;
    List<String> slots;
}
