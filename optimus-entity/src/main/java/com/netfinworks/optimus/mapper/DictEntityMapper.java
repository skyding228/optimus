package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.DictEntity;
import com.netfinworks.optimus.entity.DictEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictEntityMapper {
    int countByExample(DictEntityExample example);

    int deleteByExample(DictEntityExample example);

    int deleteByPrimaryKey(String id);

    int insert(DictEntity record);

    int insertSelective(DictEntity record);

    List<DictEntity> selectByExample(DictEntityExample example);

    DictEntity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DictEntity record, @Param("example") DictEntityExample example);

    int updateByExample(@Param("record") DictEntity record, @Param("example") DictEntityExample example);

    int updateByPrimaryKeySelective(DictEntity record);

    int updateByPrimaryKey(DictEntity record);
}