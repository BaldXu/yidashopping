package com.wuzhi.controlled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuzhi.entity.User;
import com.wuzhi.mapper.UserMapper;
import com.wuzhi.util.MD5Encryption;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-11-18
 */
@RestController
@CrossOrigin
@RequestMapping("/user")//http://localhost:8181/user/
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/find/findAll")//http://localhost:8181/user/find/findAll
    public List findAll(){
        List<User> users=userMapper.selectList(null);
        return users;
    }

    @GetMapping("/find/findById/{id}")//http://localhost:8181/user/find/findById/1
    public User findById(@PathVariable("id")int id){
        User user = userMapper.selectById(id);
        return user;
    }
    @GetMapping("/tologin")
    public String tologin(){
        return "要登录";
    }

    //注册
    @PostMapping("/register/{nickname}/{password}/{email}/{roleid}")//http://localhost:8181/user/register/1111/123/22114@qq.com/1
    public String register(@PathVariable("nickname")String nickname,
                           @PathVariable("password")String password,
                           @PathVariable("email")String email,
                           @PathVariable("roleid")int roleid){
        User user= new User();
        MD5Encryption md5Encryption=new MD5Encryption();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        List<User> users=userMapper.selectList(wrapper);
        if(roleid==1||roleid==2){
            if(users.size() == 0){
                user.setNickname(nickname);
                user.setRoleId(roleid);
                user.setEmail(email);
                user.setTime(new Date());
                user.setPassword(md5Encryption.MD5Encryption(password));
                userMapper.insert(user);
                int id = user.getId();
                return id+"注册成功";
            }
            else{
                return "邮箱已注册";
            }
        }else{                  //注册失败，跳转至register页面
            return "注册失败";
        }

    }

    @GetMapping("/loginById/{id}/{password}")//http://localhost:8181/user/loginById/1/123456
    public String loginById(@PathVariable("id") String id,@PathVariable("password") String password){
        System.out.println("yunx");
        Model model=new Model() {
            @Override
            public Model addAttribute(String s, Object o) {
                return null;
            }

            @Override
            public Model addAttribute(Object o) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> collection) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public boolean containsAttribute(String s) {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(id, password);
        try {
            User user=userMapper.selectById(id);
            int roleid=user.getRoleId();
            subject.login(token);
            return id;
        }catch (UnknownAccountException e){//用户不存在
            model.addAttribute("msg"," 用户不存在！！！");
            return "用户不存在";

        }
        catch (IncorrectCredentialsException e){//密码错误
            model.addAttribute("msg"," 密码错误！！！");
            return "密码错误";
        }
    }

    @GetMapping("/loginByemail/{useremail}/{password}")//http://localhost:8181/user/loginByemail/123@qq.com/123456
    public String loginByemail(@PathVariable("useremail") String useremail,@PathVariable("password") String password){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("email",useremail);
         List<User> users= userMapper.selectList(wrapper);
         if (users.size()==0){
             return "emailError";
         }
         User user= users.get(0);
         String id= user.getId().toString();
        System.out.println(id);
        Model model=new Model() {
            @Override
            public Model addAttribute(String s, Object o) {
                return null;
            }

            @Override
            public Model addAttribute(Object o) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> collection) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> map) {
                return null;
            }

            @Override
            public boolean containsAttribute(String s) {
                return false;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(id, password);
        try {
            subject.login(token);
            subject.login(token);
            System.out.println("登录成功");
            return id;
        }catch (UnknownAccountException e){//用户不存在
            model.addAttribute("msg"," 用户不存在！！！");
            System.out.println("该邮箱不存在");
            return "emailError";

        }
        catch (IncorrectCredentialsException e){//密码错误
            model.addAttribute("msg"," 密码错误！！！");
            System.out.println("密码错误");
            return "passwordError";
        }
    }
    @GetMapping("/showThisUserRoldId")//http://localhost:8181/user/showThisUserRoldId
    public int showUserRoldId(){
        //拿到当前登录的这个对象
        Subject subject= SecurityUtils.getSubject();
        User currentUser =(User) subject.getPrincipal();//拿到user对象
        int roleid=currentUser.getRoleId();
        return roleid;
    }
    @GetMapping("/showThisUserId")//http://localhost:8181/user/showThisUserRoldId
    public int showUserId(){
        //拿到当前登录的这个对象
        Subject subject= SecurityUtils.getSubject();
        User currentUser =(User) subject.getPrincipal();//拿到user对象
        int id=currentUser.getId();
        return id;
    }

    @GetMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权不许进入此页面";
    }

    @GetMapping("/monthlyRegistration/{month}")
    public String monthlyRegistration(@PathVariable("month") int month){
        //获取时间
        Calendar cal = Calendar.getInstance();
        int thisYear=cal.get(Calendar.YEAR);
        int thisMonth=month;
        String monthStart=thisYear+"-"+thisMonth+"-01";
        String monthEnd=thisYear+"-"+thisMonth+"-30";
        //获取当前月份的所有注册用户数量
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper
                .between("time",monthStart,monthEnd);
        List<User> users=userMapper.selectList(wrapper);
        int num=users.size();
        System.out.println(num);
        return thisMonth+"月注册量为:"+num;
    }
    @GetMapping("/monthlyRegistration/{year}/{month}")
    public String monthlyRegistrationAddYear(@PathVariable("year") int year,@PathVariable("month") int month){
        String monthStart=year+"-"+month+"-01";
        String monthEnd=year+"-"+month+"-30";
        //获取当前月份的所有注册用户数量
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper
                .between("time",monthStart,monthEnd);
        List<User> users=userMapper.selectList(wrapper);
        int num=users.size();
        System.out.println(num);
        return year+"年"+month+"月注册量为:"+num;
    }

}
