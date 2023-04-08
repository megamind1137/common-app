package com.ms.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {

    @GetMapping("/get")
    public  String get(){
        return "Testing Docker image";
    }
}
