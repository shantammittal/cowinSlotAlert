package com.example.cowinBooking.service;

import com.example.cowinBooking.model.CalendarResponseSchema;
import com.example.cowinBooking.model.CalendarResponseSchemaList;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CowinService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${pinCode}")
    String pinCode;

    @Value("${ageLimit}")
    String ageLimit;

    @Value("${url}")
    String url;

    @Scheduled(cron = "${frequency}")
    public void findByPinCodeAndDateCron() throws IOException, URISyntaxException {
        log.info(System.getProperty("os.name"));
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("user-agent", "Mozilla/5.0");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<CalendarResponseSchemaList> calendarResponseSchemaList = restTemplate.exchange(String.format("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=%s&date=%s", pinCode, localDate), HttpMethod.GET, request, CalendarResponseSchemaList.class);
        Stream<CalendarResponseSchema> calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0 && session.getMin_age_limit() >= Integer.parseInt(ageLimit)).count() > 0);
        List<CalendarResponseSchema> collect = calendarResponseSchemaStream.collect(Collectors.toList());


        if (collect.size() > 0) {
            Runtime runtime = Runtime.getRuntime();

            if (System.getProperty("os.name").contains("Windows")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (System.getProperty("os.name").contains("Mac")) {
                runtime.exec("open " + url);

            }

        }
    }
}