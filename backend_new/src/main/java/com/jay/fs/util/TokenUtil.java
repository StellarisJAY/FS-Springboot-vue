package com.jay.fs.util;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * token工具，以及全局token表
 * @author Jay
 */
@Component
public class TokenUtil {
    // 全局的token表
    private static Map<String, Integer> tokenMap = new HashMap<String, Integer>();

    // 通过 token 获取用户id
    public static int getUserId(String token){
        return tokenMap.get(token);
    }

    /**
     * 记录新登录的用户的token和对应id
     * @param token
     * @param user_id
     */
    public static void putToken(String token, int user_id){
        tokenMap.put(token, user_id);
    }

    /**
     * 验证 token，验证token是否存在，不存在将视为伪造的token
     * @param token
     * @return
     */
    public static boolean verify(String token){
        return tokenMap.containsKey(token);
    }

    /**
     * 删除token，即删除某用户的登陆状态
     * @param token
     */
    public static void removeToken(String token){
        tokenMap.remove(token);
    }

    /**
     * 生成token，通过username和password生成独一无二的token值
     * @param username
     * @param password
     * @return
     */
    public static String getToken(String username, String password){
        return username+password;
    }
}
