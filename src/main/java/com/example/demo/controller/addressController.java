package com.example.demo.controller;

import com.example.demo.mapper.TransactionDetailMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.po.TransactionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class addressController {

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @GetMapping("/getBlance")
    public Double getBlance(@RequestParam String address){
        Double blance = transactionDetailMapper.getBlance(address);
        return  blance;
}





}
