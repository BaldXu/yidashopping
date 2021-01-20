package com.wuzhi.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Encryption {
    private final static String salt="WUZHI";//偷懒了，直接把盐给固定了
    private final static int hashIterations = 1024;

    public String MD5Encryption(Object password){
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        Object source = password;
        //加密次数
        SimpleHash resultPassword = new SimpleHash("MD5", source, byteSalt, hashIterations);
        System.out.println("原密码："+password+"加密后："+resultPassword.toString());
        System.out.println(resultPassword.toString().equals("3255afb3ab188cabee0cd0fda109a02e"));
        return resultPassword.toString();
    }




}
