    package com.example.demo.controller;

import com.example.demo.dto.BlockDetailDto;
import com.example.demo.dto.BlockIndexDto;
import com.example.demo.po.Block;
import com.example.demo.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping("/blockIndex")
    public List<BlockIndexDto> blockIndex() throws Throwable {
        List<BlockIndexDto> blockIndexDtos =blockService.getBlockIndex();
        return blockIndexDtos;
    }

    @GetMapping("/blockDetail")
    public BlockDetailDto blockDetail(@RequestParam(required = false,defaultValue = "") String blockhash){
        BlockDetailDto blockDetailDto = blockService.getBlockByHash(blockhash);

        return blockDetailDto;
    }

    @GetMapping("/getBlockByHeigh")
    public  BlockDetailDto  getBlockByHeight(@RequestParam Integer height){
        BlockDetailDto blockDetailDto=  blockService.getBlockByHeight(height);
        return blockDetailDto;
    }
    @GetMapping("/blockList")
    public List<BlockIndexDto> blockList(@RequestParam String startDate,@RequestParam String endDate){
        ArrayList<BlockIndexDto> blockIndexDtos = new ArrayList<>();
        return blockIndexDtos;
    }
    @RequestMapping("/blockList/{nowDate}/{day}")
    public List<BlockIndexDto> blockList(@PathVariable String nowDate,@PathVariable Integer day){
        List<BlockIndexDto> blockIndexDtos = blockService.blockList(nowDate,day);
        return blockIndexDtos;
    }


}
