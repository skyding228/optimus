package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.EntryEntity;
import com.netfinworks.optimus.entity.EntryEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EntryEntityMapper {
    int countByExample(EntryEntityExample example);

    int deleteByExample(EntryEntityExample example);

    int deleteByPrimaryKey(Long entryId);

    int insert(EntryEntity record);

    int insertSelective(EntryEntity record);

    List<EntryEntity> selectByExample(EntryEntityExample example);

    EntryEntity selectByPrimaryKey(Long entryId);

    int updateByExampleSelective(@Param("record") EntryEntity record, @Param("example") EntryEntityExample example);

    int updateByExample(@Param("record") EntryEntity record, @Param("example") EntryEntityExample example);

    int updateByPrimaryKeySelective(EntryEntity record);

    int updateByPrimaryKey(EntryEntity record);
}