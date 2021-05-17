package com.example.cowinBooking;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestMe {

    @Test
    public  void a(){
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        System.out.println(  Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE));
    }
}
