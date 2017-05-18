package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.PaymentEntity;
import com.netfinworks.optimus.entity.PaymentEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaymentEntityMapper {
    int countByExample(PaymentEntityExample example);

    int deleteByExample(PaymentEntityExample example);

    int deleteByPrimaryKey(String id);

    int insert(PaymentEntity record);

    int insertSelective(PaymentEntity record);

    List<PaymentEntity> selectByExample(PaymentEntityExample example);

    PaymentEntity selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PaymentEntity record, @Param("example") PaymentEntityExample example);

    int updateByExample(@Param("record") PaymentEntity record, @Param("example") PaymentEntityExample example);

    int updateByPrimaryKeySelective(PaymentEntity record);

    int updateByPrimaryKey(PaymentEntity record);
}