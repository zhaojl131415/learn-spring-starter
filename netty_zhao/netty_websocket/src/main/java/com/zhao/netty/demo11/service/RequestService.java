package com.zhao.netty.demo11.service;

import com.zhao.netty.demo11.entity.Client;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;


public class RequestService {

    /**
     * 根据客户端的请求生成 Client
     *
     * @param clientInfo 客户端信息: 例如 {id:1,event:'login',token:'43606811c7305ccc6abb2be116579bfd'}
     * @return
     */
    public static Client clientRegister(String userId, String event, String equipmentCode) {
        // 解码客户端信息
        Client client = new Client();

        // 判断json是否存在直播间Id: rid
        if ("".equals(event)) {
            return client;
        }

        try {
            client.setEvent(event);
        } catch (JSONException e) {
            e.printStackTrace();
            return client;
        }

        // 判断json是否存在userId和token
        if ("".equals(userId) || "".equals(equipmentCode)) {
            return client;
        }

        Long uId = Long.parseLong(userId);
        // 验证用户token
        if (checkToken(uId, equipmentCode)) {
            client.setId(uId);
        }
        return client;
    }


    public static void main(String[] args) {
        System.out.println(Base64.encodeBase64String("{id:1,event:'login',token:'43606811c7305ccc6abb2be113456bfa'}".getBytes()));
        System.out.println(Base64.encodeBase64String("{id:1,event:'live',token:'43606811c7305ccc6abb2be113456bfb'}".getBytes()));
        System.out.println(Base64.encodeBase64String("{id:2,event:'login',token:'43606811c7305ccc6abb2be113456bfc'}".getBytes()));
        System.out.println(Base64.encodeBase64String("{id:3,event:'live',token:'43606811c7305ccc6abb2be113456bfd'}".getBytes()));


//        String clientJson = "{id:1,rid:1,token:'43606811c7305ccc6abb2be113456bfd'}";
//        String clientInfo = Base64.encodeBase64String(clientJson.getBytes());
//        System.out.println(clientInfo);
//
//        String res = new String(Base64.decodeBase64(clientInfo));
//        JSONObject json = new JSONObject(res);
//
//
//        System.out.println(json.getLong("id"));
//        System.out.println(json.getLong("rid"));
//        System.out.println(json.getString("token"));
    }

    /**
     * 从 redis 里根据 id 获取 token 与之对比
     *
     * @param id
     * @param token
     * @return
     */
    private static boolean checkToken(Long id, String token) {
        return true;
    }
}
