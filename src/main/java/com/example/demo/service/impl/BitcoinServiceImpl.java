package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.api.BlockChainApi;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.mapper.TransactionDetailMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.po.Block;
import com.example.demo.po.Transaction;
import com.example.demo.service.BitcoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class BitcoinServiceImpl implements BitcoinService {



    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlockChainApi blockChainApi;

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionDetailMapper transactionDetailMapper;

    @Override
    @Async
    @Transactional
    public void syncBlock(String blockhash) {
    logger.info("begin to sync block fron",blockhash);
        String tempBlockHash  = blockhash;
        while (tempBlockHash!=null&&!tempBlockHash.isEmpty()){
            JSONObject blockJson = blockChainApi.getBlocks(tempBlockHash);
            Block block = new Block();
            block.setBlockhash(blockJson.getString("hash"));
            block.setBlockchainId(1);
            block.setHeight(blockJson.getInteger("height"));
            Long timestamp = blockJson.getLong("time");

            Date time = new Date(timestamp * 1000);
            block.setTime(time);
            block.setTransactions(blockJson.getInteger("nTx"));
            block.setSize(blockJson.getLong("size"));
            block.setDifficulty(blockJson.getDouble("weight"));
            block.setPrevBlockhash(blockJson.getString("previousblockhash"));
            block.setNextBlockhash(blockJson.getString("nextblockhash"));
            block.setMerkleRoot(blockJson.getString("merkleroot"));
            Integer confirmations = blockJson.getInteger("confirmations");
            blockMapper.insert(block);

            JSONArray txesArray = blockJson.getJSONArray("tx");

            for(Object txobj:txesArray){
                JSONObject jsonObject = new JSONObject((LinkedHashMap) txobj);
                syncTx(jsonObject, tempBlockHash, time, confirmations);
            }

            tempBlockHash=block.getNextBlockhash();
        }
        logger.info("end sync block");

    }

    @Override
    public void syncTx(JSONObject txJson, String blockhash, Date time, Integer confirmations) {
        Transaction transaction = new Transaction();
        transaction.setTxhash(txJson.getString("txid"));
        transaction.setBlockhash(blockhash);
        transaction.setWeight(txJson.getInteger("weight"));
        transaction.setSize(txJson.getLong("size"));
        transaction.setTime(time);
        transaction.setTotalInput(null);
        transaction.setTotalOutput(null);
        transaction.setFees(null);
        transaction.setConfirm(txJson.getInteger("confirmations"));
        transactionMapper.insert(transaction);
        //todo sex tx detail
        syncTxDetail(txJson);


        // todo set tx amout


    }

    @Override
    public void syncTxDetail(JSONObject txJson) {

        JSONArray vouts = txJson.getJSONArray("vout");
        syncTxDetailVout(vouts);

        JSONArray vins = txJson.getJSONArray("vin");
        syncTxDetailVin(vins);

    }

//    支出
    @Override
    public void syncTxDetailVout(JSONArray vouts) {

        for (Object vout : vouts) {

           JSONObject jsonObject =  new JSONObject((LinkedHashMap) vout);

        }


    }

//    收入
    @Override
    public void syncTxDetailVin(JSONArray vins) {
        for (Object vin : vins) {

            JSONObject jsonObject = new JSONObject((LinkedHashMap) vin);


        }
    }


}
