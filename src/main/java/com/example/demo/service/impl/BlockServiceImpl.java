package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.api.BlockChainApi;
import com.example.demo.api.JsonRpcApi;
import com.example.demo.dto.BlockDetailDto;
import com.example.demo.dto.BlockIndexDto;
import com.example.demo.dto.BlockListDto;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.po.Block;
import com.example.demo.po.Transaction;
import com.example.demo.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public List<BlockIndexDto> getBlockIndex() throws Throwable {

        List<Block> blocks = blockMapper.getBlockIndex();
        List<BlockIndexDto> blockIndexDtos = new ArrayList<>();
        for (Block block : blocks) {
            BlockIndexDto blockIndexDto = new BlockIndexDto();
            blockIndexDto.setTime(block.getTime().getTime());
            blockIndexDto.setMiner(block.getMiner());
            blockIndexDto.setBlockhash(block.getBlockhash());
            blockIndexDto.setHeight(block.getHeight());
            blockIndexDto.setTransactions(block.getTransactions());
            blockIndexDto.setSize(block.getSize());
            blockIndexDtos.add(blockIndexDto);

        }
        return blockIndexDtos;
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

    @Override
    public BlockDetailDto getBlockByHash(String blockhash) {
        Block block = blockMapper.selectByPrimaryKey(blockhash);
        BlockDetailDto blockDetailDto = getBlockDetail(block);
        return blockDetailDto;
    }

    private BlockDetailDto getBlockDetail(Block block) {
        List<Transaction> transactions = transactionMapper.selectByBlockHash(block.getBlockhash());

            BlockDetailDto blockDetailDto = new BlockDetailDto();
        blockDetailDto.setSize(block.getSize());
        blockDetailDto.setTransactions(block.getTransactions());
        blockDetailDto.setPrevBlockhash(block.getPrevBlockhash());
        blockDetailDto.setOutputTotal(block.getOutputTotal());
        blockDetailDto.setNextBlockhash(block.getNextBlockhash());
        blockDetailDto.setMiner(block.getMiner());
        blockDetailDto.setMerkleRoot(block.getMerkleRoot());
        blockDetailDto.setHeight(block.getHeight());
        blockDetailDto.setFees(block.getFees());
        blockDetailDto.setDifficulty(block.getDifficulty());
        blockDetailDto.setBlockhash(block.getBlockhash());
        blockDetailDto.setBlockchainId(block.getBlockchainId());
        blockDetailDto.setTime(block.getTime().getTime());
        blockDetailDto.setTimestamp(block.getTimestamp().getTime());
        return blockDetailDto;


    }


}
