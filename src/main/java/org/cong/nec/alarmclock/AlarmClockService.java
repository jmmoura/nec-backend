package org.cong.nec.alarmclock;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AlarmClockService {

    private final RestClient restClient;

    public AlarmClockService(@Value("${server.port}") int serverPort) {
        RestClient.Builder restClientBuilder = RestClient.builder();
        String baseUrl = "http://localhost:" + serverPort;
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public String performSelfCall() {
        return restClient.get()
                .uri("/api/v1/alarm-clock/trigger")
                .retrieve()
                .body(String.class);
    }

}
