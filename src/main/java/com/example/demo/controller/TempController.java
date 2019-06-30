package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.api.BlockChainApi;
import com.example.demo.api.ExchangeRateApi;
import com.example.demo.api.JsonRpcApi;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.po.Block;
import com.example.demo.service.BitconinSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/temp")
public class TempController {
    @Autowired
    private BlockChainApi blockChainApi;
    @Autowired
    private JsonRpcApi jsonRpcApi;
    @Autowired
    private BitconinSyncService syncService;
    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private ExchangeRateApi exchangeRateApi;



    @GetMapping("/test1")

    public void test() throws Throwable {
        syncService.syncBlockChain("000000000000048eee82a2b1f8a4c8109605c36158ce05a54da6ae8183976b7d");
    }
    @GetMapping("/test")
    public JSONObject getTest(){
        JSONObject rate = exchangeRateApi.getRate();
        return rate;
    }


}
