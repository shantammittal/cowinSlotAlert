package com.example.cowinBooking.model;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class Mobile implements Serializable {

    @Size(min = 10, max = 10)
    long mobile;
}
