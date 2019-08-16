package ca.jrvs.apps.trading.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {

    @GetMapping("health")
    @ResponseStatus(HttpStatus.OK)
    public String health() {
        return "I'm very healthy and happy!";
    }
}
