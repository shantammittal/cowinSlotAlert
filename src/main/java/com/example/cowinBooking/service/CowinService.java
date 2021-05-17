package com.example.cowinBooking.service;

import com.example.cowinBooking.model.CalendarResponseSchema;
import com.example.cowinBooking.model.CalendarResponseSchemaList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CowinService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${pinCode}")
    String pinCode;

    @Value("${ageLimit}")
    String ageLimit;

    /*public List<CalendarResponseSchema> findByPinCodeAndDate() {
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.print("checking ");
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("user-agent", "Mozilla/5.0");
        HttpEntity request = new HttpEntity(headers);
        LocalDate.now();
        ResponseEntity<CalendarResponseSchemaList> calendarResponseSchemaList = restTemplate.exchange("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=+" + pinCode + "&date=" + localDate, HttpMethod.GET, request, CalendarResponseSchemaList.class);
        System.out.print(calendarResponseSchemaList.getStatusCode() + "  size: ");

        Stream<CalendarResponseSchema> calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0 && session.getMin_age_limit() >= Integer.parseInt(ageLimit)).count() > 0);
//        Stream<CalendarResponseSchema> calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0 && session.getMin_age_limit()==45).collect(Collectors.toList()).size() > 0);  //use this for slots age group >45 only

        List<CalendarResponseSchema> collect = calendarResponseSchemaStream.collect(Collectors.toList());
        System.out.println(collect.size());

        return collect;
    }*/


    @Scheduled(cron = "0 */2 * * * *")
    public List<CalendarResponseSchema> findByPinCodeAndDateCron() throws IOException, URISyntaxException {
        System.out.println(ageLimit);
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("user-agent", "Mozilla/5.0");
        HttpEntity request = new HttpEntity(headers);
        LocalDate.now();
        ResponseEntity<CalendarResponseSchemaList> calendarResponseSchemaList = restTemplate.exchange("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=132103&date=" + localDate, HttpMethod.GET, request, CalendarResponseSchemaList.class);
        System.out.print(calendarResponseSchemaList.getStatusCode() + "  size: ");

        Stream<CalendarResponseSchema> calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0 && session.getMin_age_limit() >= Integer.parseInt(ageLimit)).count() > 0);
//        Stream<CalendarResponseSchema> calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0 && session.getMin_age_limit()==45).collect(Collectors.toList()).size() > 0);  //use this for slots age group >45 only

        List<CalendarResponseSchema> collect = calendarResponseSchemaStream.collect(Collectors.toList());
        System.out.println(collect.size());
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        System.out.println(Desktop.isDesktopSupported());
        if (collect.size() > 0 && Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {

            desktop.browse(new URL("www.youtube.com").toURI());
        } else {
            System.out.println("nahi chala");
        }

        return collect;
    }
}