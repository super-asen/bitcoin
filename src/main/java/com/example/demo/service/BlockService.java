package com.example.demo.service;

import com.example.demo.dto.BlockDetailDto;
import com.example.demo.dto.BlockIndexDto;
import com.example.demo.po.Block;
import com.example.demo.po.Transaction;
import com.example.demo.vo.Txs;

import java.util.List;

public interface BlockService {
    List<BlockIndexDto> getBlockIndex() throws Throwable;

    BlockDetailDto getBlockByHeight(Integer height);

    BlockDetailDto getBlockByHash(String blockhash);

    BlockDetailDto getBlockDetail(Block block);

    Txs getTxs(Transaction transaction);

    BlockDetailDto searchBlockByHash(String blockhash);

    Block searchBlockByHeight(int height);

    List<BlockIndexDto> blockList(String nowDate, Integer day);
}
