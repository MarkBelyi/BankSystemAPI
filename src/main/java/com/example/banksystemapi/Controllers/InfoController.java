package com.example.banksystemapi.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @GetMapping("/info")
    public String info(){
        return "THIS IS BANK SYSTEM API VERSION 0.001, if you see this message your CONNECTION IS OK";
    }

}
