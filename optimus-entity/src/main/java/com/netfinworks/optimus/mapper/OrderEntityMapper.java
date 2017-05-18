package com.netfinworks.optimus.mapper;

import com.netfinworks.optimus.entity.OrderEntity;
import com.netfinworks.optimus.entity.OrderEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderEntityMapper {
    int countByExample(OrderEntityExample example);

    int deleteByExample(OrderEntityExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    List<OrderEntity> selectByExample(OrderEntityExample example);

    OrderEntity selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") OrderEntity record, @Param("example") OrderEntityExample example);

    int updateByExample(@Param("record") OrderEntity record, @Param("example") OrderEntityExample example);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);
}