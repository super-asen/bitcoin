package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.api.ExchangeRateApi;
import com.example.demo.dto.AddressDto;
import com.example.demo.dto.TransactionGetDto;
import com.example.demo.dto.TransactionIndexDto;
import com.example.demo.service.TransationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransationService transationService;

    @Autowired
    private ExchangeRateApi exchangeRateApi;
    @GetMapping("/txIndex")
    public List<TransactionIndexDto> txIndex(){
        List<TransactionIndexDto> transactionIndexDtos = transationService.getTxIndex();
        return transactionIndexDtos;
    }

    @GetMapping("/moreTx")
    public List<TransactionIndexDto> moreTx(){
        List<TransactionIndexDto> transactionIndexDtos = transationService.getMoreTx();
        return transactionIndexDtos;
    }
    @GetMapping("/txDeatil")
    public TransactionGetDto txDeatil(@RequestParam String txhash){
        TransactionGetDto txDeatil = transationService.getTxDeatil(txhash);
        return txDeatil;
    }

    @GetMapping("/getAddress")
    public AddressDto getAddress(@RequestParam String address){
        AddressDto address1 = transationService.getAddress(address);
        return address1;
    }

    @GetMapping("/getRate")
    public Double getRate(){
        JSONObject rate = exchangeRateApi.getRate();
        JSONObject usd = rate.getJSONObject("USD");
        Double last = usd.getDouble("last");
        return last;
    }


}
