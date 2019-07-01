package com.example.demo.controller;

import com.example.demo.dto.AddressDto;
import com.example.demo.dto.BlockDetailDto;
import com.example.demo.dto.TransactionGetDto;

import com.example.demo.po.Block;
import com.example.demo.service.BlockService;
import com.example.demo.service.TransationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class BlockChainController {

    @Autowired
    private BlockService blockService;
    @Autowired
    private TransationService transationService;

    @GetMapping("/search")
    public Object getSearch(@RequestParam(required = false,defaultValue = "")String  param) {
        param=param.trim();
        AddressDto addressDto =transationService.getAddress(param);
        if(addressDto!=null){
            return "AddressDetail.html?address="+addressDto.getAddress();
        }else{
            String reg="^\\d+$";
            if(param.matches(reg)){
                Block block = blockService.searchBlockByHeight(Integer.parseInt(param));
                if(block!=null){
                    return "BlockByHeight.html?height="+block.getHeight();
                }
            }else{
                if(param.length()==64){
                    BlockDetailDto blockDetailDto = blockService.searchBlockByHash(param);
                    if(blockDetailDto!=null){
                        return "BlockByHash.html?blockhash="+blockDetailDto.getBlockhash();
                    }else{
                        TransactionGetDto transactionGetDto = transationService.seaerchTransactionByTxhash(param);
                        if(transactionGetDto !=null){
                            return "TransationByHash.html?txhash="+transactionGetDto.getTxhash();
                        }
                    }
                }
            }
        }
        return null;
    }
}



