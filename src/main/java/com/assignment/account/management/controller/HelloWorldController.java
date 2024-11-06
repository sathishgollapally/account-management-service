package com.assignment.account.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping
    public String test () {
        return "Hello World";
    }

}
