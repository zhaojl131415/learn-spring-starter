package com.zhao.seata.order.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    @Insert("insert into order(name) values(#{name})")
    public void insert(@Param("name") String name);
}
