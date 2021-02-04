package com.example.fakeitunes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String home() {
        return "This is home page";
    }

    @GetMapping("/search")
    public String searchResult(@RequestParam String term) {
        return "Search with " + term;
    }
}
