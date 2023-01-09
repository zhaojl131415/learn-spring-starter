package com.zhao.shardingSphere.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author ：楼兰
 * @description:
 **/
@TableName("t_user")
public class User {
    private Long userid;
    private String username;
    private String ustatus;
    private int uage;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userid +
                ", username='" + username + '\'' +
                ", ustatus='" + ustatus + '\'' +
                ", uage=" + uage +
                '}';
    }

    public int getUage() {
        return uage;
    }

    public void setUage(int uage) {
        this.uage = uage;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

}
