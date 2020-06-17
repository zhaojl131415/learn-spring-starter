package com.zhao.mybatis.mapper;


import com.zhao.mybatis.annotation.ZhaoSelect;

import java.util.List;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-03-08 14:39
 */
public interface UserMapper {

	@ZhaoSelect("select * from user")
	public List<String> queryUser();

}
