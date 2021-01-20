package com.wuzhi;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


import java.util.ArrayList;

public class AutoCode {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        //配置策略
        //1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        System.out.println("生成的路径是  "+projectPath+"\\src\\main\\java");
        gc.setOutputDir(projectPath+"\\src\\main\\java");//这样生成的所有东西都会在这个路径下了
        gc.setFileOverride(true);//是否覆盖
        gc.setOpen(true);
        gc.setIdType(IdType.ID_WORKER);//主键生成策略
        gc.setDateType(DateType.ONLY_DATE);//日期类型
        mpg.setGlobalConfig(gc);


        //2.数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/wuzhi?useSSL=false&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);//数据库类型
        mpg.setDataSource(dsc);

        //3.确定要生成什么包
        PackageConfig pc=new PackageConfig();
        pc.setParent("com.wuzhi");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controlled");
        mpg.setPackageInfo(pc);

        //4.策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);//命名规则，下划线变驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//列的名字改变
        strategy.setEntityLombokModel(true);//自动lombok
        strategy.setLogicDeleteFieldName("deleted");//逻辑删除
        //重点，表的映射
//        String[] includeArr={"clo_type","admin_info","clothing","customer_info","jurisdiction","log","record","rent_list","role","sell_list","statistice","stock","store_info","user","user_role"};
        String[] includeArr={"star_list"};
        strategy.setInclude(includeArr);
        //自动填充策略
        TableFill gmtCreate = new TableFill("gmtCreate", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmtModified", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();

        tableFills.add(gmtCreate);
        tableFills.add(gmtModified);
        strategy.setTableFillList(tableFills);

        //乐观锁
        strategy.setVersionFieldName("version");
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);//命名规则,localhost:8080/hello_id_2
        mpg.setStrategy(strategy);


        mpg.execute();
    }
}
