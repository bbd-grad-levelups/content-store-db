package com.content.repository.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SearchController {

    @GetMapping
    public ResponseEntity<String> search() {
        return new ResponseEntity<>("Hello world", HttpStatus.OK);
    }
}
