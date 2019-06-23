package com.example.demo.controller;

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

    @GetMapping("/txIndex")
    public List<TransactionIndexDto> txIndex(){
        List<TransactionIndexDto> transactionIndexDtos = transationService.getTxIndex();
        return transactionIndexDtos;
    }
    @GetMapping("/txDeatil")
    public TransactionGetDto txDeatil(@RequestParam(required = false,defaultValue = "") String txhash){
        TransactionGetDto transactionGetDto = transationService.getTxDeatil(txhash);
        return transactionGetDto;
    }
}
