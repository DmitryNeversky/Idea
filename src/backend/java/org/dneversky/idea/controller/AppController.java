package org.dneversky.idea.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AppController {

    @GetMapping
    public ResponseEntity<Object> getPage(HttpServletRequest request){

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
