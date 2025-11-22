package com.journal.Journal.service;

import com.journal.Journal.api.response.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String weatherApiKey;
    @Autowired
    private RestTemplate restTemplate;
    private static final String fullUrl = "https://api.openweathermap.org/data/2.5/weather?q=CITY&appid=API_KEY";
    public WeatherApiResponse getWeatherInfo(String city) {
        String finalApi = fullUrl.replace("CITY",city).replace("API_KEY",weatherApiKey);
        ResponseEntity<WeatherApiResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherApiResponse.class);
        WeatherApiResponse body = response.getBody();
        return body;
    }
}
