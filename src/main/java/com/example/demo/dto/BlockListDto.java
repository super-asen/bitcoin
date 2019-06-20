package com.example.demo.dto;

import com.example.demo.po.Block;

import java.util.List;

public class BlockListDto extends Block {

    List<TransactionIndexDto> transactionIndexDtos;


    public List<TransactionIndexDto> getTransactionIndexDtos() {
        return transactionIndexDtos;
    }

    public void setTransactionIndexDtos(List<TransactionIndexDto> transactionIndexDtos) {
        this.transactionIndexDtos = transactionIndexDtos;
    }
}
