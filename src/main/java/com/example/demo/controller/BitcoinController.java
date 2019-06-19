package com.example.demo.controller;

import com.example.demo.service.BitcoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/Bitcoin")
@EnableAutoConfiguration
public class BitcoinController {

    @Autowired
    private BitcoinService bitcoinService;




    @GetMapping("/test")

    public String test() throws Throwable {


        String blockhash="0000000000001c7f07550db1ad91e7b32b06f12ad0cb6414f0c7efa5fe8244fb";
        bitcoinService.syncBlock(blockhash);


        return null;
    }





}
