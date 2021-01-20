package com.wuzhi.config;

import com.wuzhi.entity.Role;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.RoleMapper;
import com.wuzhi.mapper.UserMapper;
import com.wuzhi.util.MD5Encryption;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

public class UserRealm extends AuthorizingRealm {
    //获取数据库
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    //授权（1.获取当前用户 2.从数据库中获取该用户的权限，并设置进去
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();//如果进入了一些被拦截的页面就会进行授权
        //拿到当前登录的这个对象
        Subject subject= SecurityUtils.getSubject();
        User currentUser =(User) subject.getPrincipal();//拿到user对象
        int roleid=currentUser.getRoleId();
        Role role = roleMapper.selectById(roleid);
        String jurisdiction = role.getJurisdiction();
        String[] perms=jurisdiction.split(",");
        for (String perm:perms){
            info.addStringPermission(perm);
        }
        System.out.println(role.getRoleName());
        info.addRole(role.getRoleName());
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");
        //获取用户名，密码
        UsernamePasswordToken userToken=(UsernamePasswordToken) token;//输入密码
        User user = userMapper.selectById(userToken.getUsername());//数据库的密码
        if (user==null){
            return null;//抛出异常
        }
        //盐
        ByteSource byteSalt = ByteSource.Util.bytes("WUZHI");
        //密码认证是他自己做的，你做的话可能会泄露
        return new SimpleAuthenticationInfo(user,user.getPassword(),byteSalt,getName());//其实是getpassword，但是这里的数据库没有写，所以就算了
    }
    }
