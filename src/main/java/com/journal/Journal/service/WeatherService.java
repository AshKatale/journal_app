package com.journal.Journal.service;

import com.journal.Journal.api.response.WeatherApiResponse;
import com.journal.Journal.cache.AppCache;
import com.journal.Journal.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
//    @Value("${weather.api.key}")
//    private String weatherApiKey;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;
    public WeatherApiResponse getWeatherInfo(String city) {
        String finalApi = appCache.APP_CACHE.get(AppCache.keys.WEATHER_URI.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,appCache.APP_CACHE.get(AppCache.keys.WEATHER_API_KEY.toString()));
        ResponseEntity<WeatherApiResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET,null, WeatherApiResponse.class);
        WeatherApiResponse body = response.getBody();
        return body;
    }
}
