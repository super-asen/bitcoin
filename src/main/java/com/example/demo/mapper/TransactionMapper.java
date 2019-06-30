package com.example.demo.mapper;

import com.example.demo.po.Transaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TransactionMapper {
    int deleteByPrimaryKey(String txhash);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(@Param("txhash") String txhash);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<Transaction> selectByBlockHash(String blockhash);

    List<Transaction> getTxIndex();

    List<Transaction> getMoreTx();
}