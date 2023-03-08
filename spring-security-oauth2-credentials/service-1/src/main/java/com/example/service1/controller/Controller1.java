package com.example.service1.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class Controller1 {

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();
    private final WebClient webClient = WebClient.builder().build();


    @GetMapping("/service1/home")
    @ResponseStatus(HttpStatus.OK)
    public String helloRestTemplate() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8084/service2/home", HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        return "Hello " + response.getBody();
    }

    @GetMapping("/service1/home/webclient")
    @ResponseStatus(HttpStatus.OK)
    public String helloWebClient() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = webClient.get()
                .uri("http://localhost:8084/service2/home")
                .headers(header -> header.setBearerAuth(jwt.getTokenValue()) )
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "Hello " + response;
    }
}
