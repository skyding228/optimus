package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.AccountEntity;
import com.netfinworks.optimus.entity.AccountEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountEntityMapper {
    int countByExample(AccountEntityExample example);

    int deleteByExample(AccountEntityExample example);

    int deleteByPrimaryKey(String accountId);

    int insert(AccountEntity record);

    int insertSelective(AccountEntity record);

    List<AccountEntity> selectByExample(AccountEntityExample example);

    AccountEntity selectByPrimaryKey(String accountId);

    int updateByExampleSelective(@Param("record") AccountEntity record, @Param("example") AccountEntityExample example);

    int updateByExample(@Param("record") AccountEntity record, @Param("example") AccountEntityExample example);

    int updateByPrimaryKeySelective(AccountEntity record);

    int updateByPrimaryKey(AccountEntity record);
}