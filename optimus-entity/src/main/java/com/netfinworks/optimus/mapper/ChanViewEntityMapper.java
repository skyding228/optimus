package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.ChanViewEntity;
import com.netfinworks.optimus.entity.ChanViewEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChanViewEntityMapper {
    int countByExample(ChanViewEntityExample example);

    int deleteByExample(ChanViewEntityExample example);

    int deleteByPrimaryKey(String id);

    int insert(ChanViewEntity record);

    int insertSelective(ChanViewEntity record);

    List<ChanViewEntity> selectByExample(ChanViewEntityExample example);

    ChanViewEntity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ChanViewEntity record, @Param("example") ChanViewEntityExample example);

    int updateByExample(@Param("record") ChanViewEntity record, @Param("example") ChanViewEntityExample example);

    int updateByPrimaryKeySelective(ChanViewEntity record);

    int updateByPrimaryKey(ChanViewEntity record);
}