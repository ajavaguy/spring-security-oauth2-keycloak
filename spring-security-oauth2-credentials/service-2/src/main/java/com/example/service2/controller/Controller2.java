package com.example.service2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller2 {

    @GetMapping("/service2/home")
    @ResponseStatus(HttpStatus.OK)
    public String helloRestTemplate() {
        return "message from service 2";
    }
}