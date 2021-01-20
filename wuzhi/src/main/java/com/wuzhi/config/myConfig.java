package com.wuzhi.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.LinkedHashMap;
import java.util.Map;

@MapperScan("com.wuzhi.mapper")
@EnableTransactionManagement
@Configuration
public class myConfig {

    //shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        /*
         * anon:无需认证
         * authc:必须认证
         * user:必须拥有“记住我”功能（我不用
         * perms:必须拥有对某个资源的权限才能访问
         * roles:某个角色才可以访问
         * */
        Map<String, String> filterMap = new LinkedHashMap<>();

//        filterMap.put("/user/add","perms[user:add]");
//        filterMap.put("/user/add/*","perms[user:add]");
//        filterMap.put("/user/add/**","perms[user:add]");
//        filterMap.put("/user/add","roles[admin]");
        filterMap.put("/user/update","roles[users]");
//        filterMap.put("/user/update","perms[user:update]");
//        filterMap.put("/user/**","authc");

        //忽略
        filterMap.put("/user/zhuce","anon");
        filterMap.put("/login","anon");
        filterMap.put("/login/*","anon");
        filterMap.put("/login/**","anon");

        bean.setFilterChainDefinitionMap(filterMap);
        //如果没有权限
        bean.setLoginUrl("/user/tologin");//登录页面
        bean.setUnauthorizedUrl(("/user/noauth"));//未授权页面


        return bean;
    }
    //DefaultWebSecurityManager
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){//Realm已经被spring接管了，应该从spring里拿
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联userrealm(
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象，需要自定义类
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        UserRealm myShiroRealm = new UserRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return  myShiroRealm;
    }


    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);// 散列的次数，比如散列两次，相当于md5(md5(""));
        return hashedCredentialsMatcher;
    }

}
