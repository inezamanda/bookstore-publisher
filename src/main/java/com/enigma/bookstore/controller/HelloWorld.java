package com.enigma.bookstore.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class HelloWorld {

    @GetMapping("/hello")
    String hello() {
        return "Hello World";
    }

    @GetMapping("hello/{var}")
    public String pathVar(@PathVariable String var) {
        return  "Path var: " + var;
    }

    @GetMapping("/req-query")
    public String queryString(@RequestParam String var) {
        return "Request param: " + var;
    }

    @PostMapping("/hello-body")
    public String reqBody(@RequestBody HashMap<String, String> mapBody){
        return "Request Body: " + mapBody;
    }
}
