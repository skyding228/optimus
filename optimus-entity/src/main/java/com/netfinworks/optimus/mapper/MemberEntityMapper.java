package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.MemberEntity;
import com.netfinworks.optimus.entity.MemberEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberEntityMapper {
    int countByExample(MemberEntityExample example);

    int deleteByExample(MemberEntityExample example);

    int deleteByPrimaryKey(String memberId);

    int insert(MemberEntity record);

    int insertSelective(MemberEntity record);

    List<MemberEntity> selectByExample(MemberEntityExample example);

    MemberEntity selectByPrimaryKey(String memberId);

    int updateByExampleSelective(@Param("record") MemberEntity record, @Param("example") MemberEntityExample example);

    int updateByExample(@Param("record") MemberEntity record, @Param("example") MemberEntityExample example);

    int updateByPrimaryKeySelective(MemberEntity record);

    int updateByPrimaryKey(MemberEntity record);
}