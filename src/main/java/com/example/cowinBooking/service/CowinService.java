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

    @Value("${districtId}")
    String districtId;

    @Value("${age}")
    String ageLimit;

    @Value("${url}")
    String url;

    @Scheduled(cron = "${frequency}")
    public void findByPinCodeAndDateCron() throws Exception {

        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("user-agent", "Mozilla/5.0");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<CalendarResponseSchemaList> calendarResponseSchemaList;
        if (!(districtId == null || districtId.isEmpty())) {
            log.info("findByDistrict");
            calendarResponseSchemaList = findByDistrict(localDate, request);
        } else {
            log.info("findPincode");
            calendarResponseSchemaList = findByPinCode(localDate, request);
        }

        Stream<CalendarResponseSchema> calendarResponseSchemaStream;

        if (ageLimit == null || ageLimit.isEmpty()) {
            calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0).count() > 0);
        } else {
            calendarResponseSchemaStream = calendarResponseSchemaList.getBody().getCenters().stream().filter(calendarResponseSchema -> calendarResponseSchema.getSessions().stream().filter(session -> session.getAvailable_capacity() > 0 && session.getMin_age_limit() == Integer.parseInt(ageLimit)).count() > 0);
        }
        List<CalendarResponseSchema> collect = calendarResponseSchemaStream.collect(Collectors.toList());
        log.info("size = " + collect.size());

        if (collect.size() > 0) {
            Runtime runtime = Runtime.getRuntime();
            log.info("Slot available for booking. Head to www.cowin.gov.in");
            if (System.getProperty("os.name").contains("Windows")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + "https://selfregistration.cowin.gov.in/");
                trayNotification();
            } else if (System.getProperty("os.name").contains("Mac")) {
                runtime.exec("open " + url);
                runtime.exec("open " + "https://selfregistration.cowin.gov.in/");
            }
        }
    }

    ResponseEntity<CalendarResponseSchemaList> findByPinCode(String localDate, HttpEntity request) {
        return restTemplate.exchange(String.format("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=%s&date=%s", pinCode, localDate), HttpMethod.GET, request, CalendarResponseSchemaList.class);
    }

    ResponseEntity<CalendarResponseSchemaList> findByDistrict(String localDate, HttpEntity request) {
        return restTemplate.exchange(String.format("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=%s&date=%s", districtId, localDate), HttpMethod.GET, request, CalendarResponseSchemaList.class);
    }

    public void trayNotification() throws Exception {
        Image image = Toolkit.getDefaultToolkit().createImage("");
        SystemTray tray = SystemTray.getSystemTray();

        TrayIcon trayIcon = new TrayIcon(image, "Slots");

        trayIcon.setImageAutoSize(true);

        trayIcon.setToolTip("Slots available");
        tray.add(trayIcon);

        trayIcon.displayMessage("Slot available ", "notification", TrayIcon.MessageType.INFO);
    }

}