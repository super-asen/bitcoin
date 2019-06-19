package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.api.BlockChainApi;
import com.example.demo.api.JsonRpcApi;
import com.example.demo.enumeration.TxdetailType;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.mapper.TransactionDetailMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.po.Block;
import com.example.demo.po.Transaction;
import com.example.demo.po.TransactionDetail;
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

    @Autowired
    private JsonRpcApi jsonRpcApi;


    @Override
    @Async
    @Transactional
    public void syncBlock(String blockhash) throws Throwable {
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
            block.setTimestamp(time);
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
    public void syncTx(JSONObject txJson, String blockhash, Date time, Integer confirmations) throws Throwable {
        Transaction transaction = new Transaction();
        String txid = txJson.getString("txid");
        transaction.setTxhash(txid);
        transaction.setBlockhash(blockhash);
        transaction.setWeight(txJson.getInteger("weight"));
        transaction.setSize(txJson.getLong("size"));
        transaction.setTime(time);
        transaction.setTotalInput(null);
        transaction.setTotalOutput(null);
        transaction.setFees(null);
        transaction.setConfirm(confirmations);
        transactionMapper.insert(transaction);
        //todo sex tx detail
        syncTxDetail(txJson,txid);
        // todo set tx amout
    }

    @Override
    public void syncTxDetail(JSONObject txJson,String txid) throws Throwable {

        JSONArray vouts = txJson.getJSONArray("vout");
        syncTxDetailVout(vouts,txid);

        JSONArray vins = txJson.getJSONArray("vin");
        syncTxDetailVin(vins,txid);

    }

//    支出
    @Override
    public void syncTxDetailVout(JSONArray vouts,String txid) {

        for (Object vout : vouts) {

           JSONObject jsonObject =  new JSONObject((LinkedHashMap) vout);

            TransactionDetail transactionDetail = new TransactionDetail();


            transactionDetail.setTxid(txid);
            transactionDetail.setType((byte) TxdetailType.Receive.ordinal());

            JSONObject scriptPubKey = jsonObject.getJSONObject("scriptPubKey");
            JSONArray addresses = scriptPubKey.getJSONArray("addresses");
            if(addresses!=null){
                String address = addresses.getString(0);
                transactionDetail.setAddress(address);
            }
            transactionDetail.setAmount(jsonObject.getDouble("value"));

            transactionDetailMapper.insert(transactionDetail);


        }


    }

//    收入
    @Override
    public void syncTxDetailVin(JSONArray vins,String txid) throws Throwable {
        for (Object vin : vins) {
            JSONObject jsonObject = new JSONObject((LinkedHashMap) vin);
         String vinstxid = jsonObject.getString("txid");
         Integer n = jsonObject.getInteger("vout");
         if(vinstxid!=null){
             JSONObject vinTxJson = jsonRpcApi.getTransactionById(vinstxid);
             JSONArray vouts = vinTxJson.getJSONArray("vout");
             JSONObject utxoJson = vouts.getJSONObject(n);
             TransactionDetail transactionDetail = new TransactionDetail();
             transactionDetail.setAmount(-utxoJson.getDouble("value"));
             transactionDetail.setTxid(txid);
             transactionDetail.setType((byte) TxdetailType.send.ordinal());
             JSONObject scriptPubKey = utxoJson.getJSONObject("scriptPubKey");
             JSONArray addresses = scriptPubKey.getJSONArray("addresses");
             if(addresses!=null){
                 String address = addresses.getString(0);
                 transactionDetail.setAddress(address);
             }
             transactionDetailMapper.insert(transactionDetail);
         }

        }
    }


}
