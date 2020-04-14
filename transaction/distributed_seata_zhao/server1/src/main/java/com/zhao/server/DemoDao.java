package com.zhao.server;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DemoDao {

    @Insert("insert into t_test1(name) values(#{name})")
    public void insert(@Param("name") String name);
}
