package com.journal.Journal.controller;

import com.journal.Journal.api.response.WeatherApiResponse;
import com.journal.Journal.entity.User;
import com.journal.Journal.service.UserService;
import com.journal.Journal.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User userInDB = userService.findByUsername(user.getUsername());
        if(userInDB != null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exist");
        userService.saveNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/weather")
    public ResponseEntity<?> getWeatherInfo() {
        WeatherApiResponse weatherApiResponse = weatherService.getWeatherInfo("Mumbai");
        if(weatherApiResponse!=null)
            return new ResponseEntity<>("Hi, Todays weather feels like :"+weatherApiResponse.getMain().getFeelsLike(),HttpStatus.OK);

        return new ResponseEntity<>("Hi No Response to show",HttpStatus.OK);
    }
}
