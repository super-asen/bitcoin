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


        String blockhash="00000000000006f980320c0c7dde2a8dee5115fc75d9c38743062604cf883087";
        bitcoinService.syncBlock(blockhash);


        return null;
    }





}
