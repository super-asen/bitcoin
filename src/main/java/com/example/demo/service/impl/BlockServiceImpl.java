package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.api.BlockChainApi;
import com.example.demo.api.JsonRpcApi;
import com.example.demo.dto.BlockIndexDto;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.po.Block;
import com.example.demo.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {
    @Autowired
    private BlockChainApi blockChainApi;

    @Autowired
    private JsonRpcApi jsonRpcApi;

    @Autowired
    private BlockMapper blockMapper;


    @Override
    public List<BlockIndexDto> getBlockIndex() throws Throwable {
        ArrayList<BlockIndexDto> blocks = new ArrayList<>();
        JSONObject chinInfo = blockChainApi.getChinInfo();
        Integer height = chinInfo.getInteger("blocks")-5;
        JSONObject blockhashByHeight = blockChainApi.getBlockhashByHeight(height);
        String blockhash = blockhashByHeight.getString("blockhash");
        List<JSONObject> blockHeaders = blockChainApi.getBlockHeaders(5, blockhash);
        for (JSONObject blockHeader : blockHeaders) {
            JSONObject block = jsonRpcApi.getBlockByHash(blockHeader.getString("hash"));
            Long size = block.getLong("size");
            BlockIndexDto blockIndexDto = new BlockIndexDto();
            blockIndexDto.setSize(size);
            blockIndexDto.setTransactions(blockHeader.getInteger("nTx"));
            blockIndexDto.setHeight(blockHeader.getInteger("height"));
            blockIndexDto.setBlockhash(blockHeader.getString("hash"));
            blockIndexDto.setTime(new Date(1000*blockHeader.getLong("time")).getTime());
            //todo
            blockIndexDto.setMiner(null);
            blocks.add(blockIndexDto);
        }
        return blocks;
    }

    @Override
    public List<BlockIndexDto> getRecentBlocks() {
        ArrayList<BlockIndexDto> blockIndexDtos = new ArrayList<>();

        List<Block> blocks = blockMapper.selectRecentBlocks();
        for (Block block : blocks) {
            BlockIndexDto blockIndexDto = new BlockIndexDto();
            blockIndexDto.setBlockhash(block.getBlockhash());
            blockIndexDto.setHeight(block.getHeight());
            blockIndexDto.setTime(block.getTime().getTime());
            blockIndexDto.setTransactions(block.getTransactions());
            blockIndexDto.setMiner(block.getMiner());
            blockIndexDto.setSize(block.getSize());
            blockIndexDtos.add(blockIndexDto);
        }
        return blockIndexDtos;
    }


}
