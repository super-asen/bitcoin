package com.example.demo.mapper;

import com.example.demo.dto.BlockIndexDto;
import com.example.demo.dto.BlockListDto;
import com.example.demo.po.Block;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlockMapper {

    int deleteByPrimaryKey(String blockhash);

    int insert(Block record);

    int insertSelective(Block record);

    Block selectByPrimaryKey(String blockhash);

    int updateByPrimaryKeySelective(Block record);

    int updateByPrimaryKey(Block record);

    List<Block> selectRecentBlocks();

    List<BlockListDto> blockHashGetAll(@Param("blockhash") String blockhash);

    List<Block> getBlockIndex();


}