package com.example.demo.api;

import com.alibaba.fastjson.JSONObject;

public interface JsonRpcApi {
    JSONObject getBlockchainInfo() throws Throwable;

    JSONObject getBlockByHash(String blockhash) throws Throwable;

    JSONObject getTransactionById(String txid) throws Throwable;

    String getBlockhashByHeight(Integer height) throws Throwable;

    JSONObject getAddressByHash(String address) throws Throwable;

}
