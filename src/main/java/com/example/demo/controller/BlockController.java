package com.example.demo.controller;

import com.example.demo.dto.BlockDetailDto;
import com.example.demo.dto.BlockIndexDto;
import com.example.demo.dto.BlockListDto;
import com.example.demo.mapper.BlockMapper;
import com.example.demo.mapper.TransactionMapper;
import com.example.demo.po.Block;
import com.example.demo.po.Transaction;
import com.example.demo.service.BlockService;
import com.example.demo.vo.TxDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;


    @Autowired
    private TransactionMapper transactionMapper;

    //页面展示数据
    @RequestMapping("/blockIndex")
    public List<BlockIndexDto> blockIndex() throws Throwable {
        List<BlockIndexDto> blockIndexDtos =blockService.getBlockIndex();
        return blockIndexDtos;
    }


    //根据hash值模糊查询
    @RequestMapping("/blockHashGetAll")
    public BlockDetailDto blockListDto(@RequestParam(required = false,defaultValue = "")String blockhash){
        BlockDetailDto blockDetailDto = blockService.getBlockByHash(blockhash);
     return blockDetailDto;
    }

    //根据高度进行模糊查询



}
