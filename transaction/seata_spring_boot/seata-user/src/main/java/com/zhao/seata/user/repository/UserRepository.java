package com.zhao.seata.user.repository;

import com.zhao.seata.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-19 12:55
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update z_user set point = ?2 where id = ?1 ", nativeQuery = true)
    void point(Integer id, Integer point);
}
