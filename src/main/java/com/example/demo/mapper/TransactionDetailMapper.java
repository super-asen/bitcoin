package com.example.demo.mapper;

import com.example.demo.po.TransactionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TransactionDetailMapper {
    int deleteByPrimaryKey(Long txDetailId);

    int insert(TransactionDetail record);

    int insertSelective(TransactionDetail record);

    TransactionDetail selectByPrimaryKey(Long txDetailId);

    int updateByPrimaryKeySelective(TransactionDetail record);

    int updateByPrimaryKey(TransactionDetail record);

    List<TransactionDetail> selectByTxId(String txhash);

    List<TransactionDetail> getByAddress(@Param("address") String address);

    double getTotalReceived(String address);

    Integer getTxSize(String address);

    double getfinalBalance(String address);
}