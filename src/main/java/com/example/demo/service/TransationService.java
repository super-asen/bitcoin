package com.example.demo.service;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.TransactionGetDto;
import com.example.demo.dto.TransactionIndexDto;
import com.example.demo.dto.TransactionSearchDto;

import java.util.List;

public interface TransationService {
    List<TransactionIndexDto> getTxIndex();

    TransactionGetDto getTxDeatil(String txhash);

    TransactionSearchDto seaerchTransactionByTxhash(String txhash);

    AddressDto getAddress(String address);

    Double getTotalReceived(String address);

    Integer getTxSize(String address);

    Double getfinalBalance(String address);

    List<TransactionIndexDto> getMoreTx();
}
