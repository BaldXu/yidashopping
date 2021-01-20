package com.mybaitsplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybaitsplus.mapper.UserMapper;
import com.mybaitsplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserMapper userMapper;//继承了basemapper，所有的方法都来自父类，当然可以自己编写扩展方法

    //查询
    @Test
    void contextLoads() {
        //查询全部用户，这里的参数是一个wrapper，条件构造器，这里先不用
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    //插入
    @Test
    public void testInsert(){
        int num=1;
        User user = new User();
        user.setAge(12223);
        user.setEmail("12d3@qq.com");

        for (int i=1;i>0;i--){
            String k="删除"+num;
            user.setName(k);
            num++;
            int result = userMapper.insert(user); //自动生成ID
            System.out.println(result);//受影响的行数
            System.out.println(user);
        }


    }

    //更新
    @Test
    public void testUpdate(){
         User user = new User();
         user.setId(7L);
        user.setName("狗子你变了2");
        user.setAge(22);
        user.setEmail("1234561513@qq.com");

        int i =userMapper.updateById(user);
        System.out.println(i);
    }

    //测试乐观锁成功案例
    @Test
    public void testOptimisticLockerInterceptor(){
        //1 查询用户信息
        User user = userMapper.selectById(7L);
        //2 修改用户信息
        user.setName("猫子");
        user.setEmail("11111@gg.com");
        //3 执行更新操作  然后应该就可以看到version+1
        userMapper.updateById(user);
    }

    //测试乐观锁失败案例  （多线程下
    @Test
    public void testOptimisticLockerInterceptor2(){
        //线程1
        User user = userMapper.selectById(7L);
        user.setName("猫子");
        user.setEmail("11111@gg.com");
        //模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(7L);
        user2.setName("猫子4");
        user2.setEmail("444444@gg.com");
        userMapper.updateById(user2);

        //使用自旋锁来多次尝试提交
        userMapper.updateById(user);//若没有乐观锁就会覆盖插队线程的值
    }

    //查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(2L);
        System.out.println(user);

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }
    //条件查询其一 map(之前有个是用wrapper查询 以后用这个方法
    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        //自定义查询条件
//        map.put("name","Tom");
        map.put("age",12223);//会自动过滤逻辑删除的字段

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage(){
        Page<User> page = new Page<>(2, 5);
        userMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }
    //删除
    @Test
    public void testDeleteById(){
        //userMapper.deleteById(8L);
//        userMapper.deleteBatchIds(Arrays.asList(18,19));
        HashMap<String, Object> map = new HashMap<>();
//        map.put("name","删除1");
        map.put("age","12223");
        userMapper.deleteByMap(map);
    }
}
