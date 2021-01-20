package com.wuzhi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuzhi.controlled.UserController;
import com.wuzhi.entity.*;
import com.wuzhi.mapper.*;
import com.wuzhi.util.MD5Encryption;
import com.wuzhi.util.addClothing;
import com.wuzhi.util.buy;
import com.wuzhi.util.updateStock;
import org.apache.ibatis.jdbc.Null;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class WuzhiApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private StoreInfoMapper storeInfoMapper;
    @Autowired
    private ClothingMapper clothingMapper;
    @Autowired
    private CloImgMapper cloImgMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SellListMapper sellListMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private CloTypeMapper cloTypeMapper;
    @Autowired
    private RentListMapper rentListMapper;
    @Autowired
    private StarListMapper starListMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    //查询
    @Test
    void userfind() {
        List<User> users=userMapper.selectList(null);
        users.forEach(System.out::println);
        User user=userMapper.selectById(1L);
        System.out.println(user.getNickname());
    }
    @Test
    void roleFind() {
        List<Role> roles=roleMapper.selectList(null);
        User user=userMapper.selectById(1L);
        System.out.println("   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 "+user.getRoleId());
        int roleid=user.getRoleId();
        System.out.println("   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 "+roleid);
        Role role = roleMapper.selectById(roleid);
        String jurisdiction = role.getJurisdiction();
        String[] perms=jurisdiction.split(",");
        for (String perm:perms){
            System.out.println("   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1 "+perm);
        }
    }
    //插入
    @Test
    public void testInsert(){
        User user=new User();
        user.setNickname("测试用的狗子");
        user.setTime(new Date());
        String password="123456";
        int roleid=2;
        String email="3333@qq.com";
        MD5Encryption md5Encryption=new MD5Encryption();
        user.setPassword(md5Encryption.MD5Encryption(password));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email);
        List<User> users=userMapper.selectList(wrapper);
        if(roleid==1||roleid==2){
            System.out.println("dd");
            if( users.size() == 0){
                System.out.println("cc");
                user.setRoleId(roleid);
                user.setEmail(email);
                int result=userMapper.insert(user);
                System.out.println(md5Encryption.MD5Encryption(password));
                System.out.println(result);
                System.out.println(user);
            }
            else{
                System.out.println("邮箱已注册");
            }
        }else{    //注册失败，跳转至register页面
            System.out.println("false");
        }
    }

    @Test
    public void testLog(){
        Logger logger=Logger.getLogger(Logger.class);
        logger.info("ceshi");
        logger.debug("debug 级别内容，测试用555hhjj25");
//        logger.error("error 级别内容，测试用");
//        logger.warn("warn 级别内容，测试用");
    }
    @Test
    public void testfile() throws Exception{
//        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        File file=new File("");
        String filePath = file.getCanonicalPath()+"\\src\\main\\resources\\img";
        System.out.println(filePath);
    }
    @Test
    public void md5test(){
        String salt="WUZHI";
        String password="123456";
        String hashAlgorithmName = "MD5";
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        Object source = password;
        //加密次数
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        System.out.println("密码：123456 盐：WUZHI 盐值加密结果："+result.toString());
        User user=userMapper.selectById(1L);
        String up=user.getPassword();
        System.out.println(up.equals(result.toString()));
    }

    @Test
    public void storeinfoTest(){
        List<StoreInfo> slist= storeInfoMapper.selectList(null);
        slist.forEach(System.out::println);
    }
    @Test
    void contextLoads4() {
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        Clothing clothing=new Clothing();
        String name="测试的衣服";
        int num=4;
        String ll="limit "+num;
        wrapper
                .eq("size","XL")
                .eq("clo_type_id",1)
                .eq("state","租出去")
                .eq("name",name)
                .last(ll);
        clothing.setState("空闲");
        clothingMapper.update(clothing,wrapper);
        Integer count = clothingMapper.selectCount(wrapper);
        System.out.println(name+"剩余库存："+count);

        }

    @Test
    public void CloImgInsert(){
        CloImg cloImg=new CloImg();
        cloImg.setCloTypeId(111);
        cloImg.setStoreId(1111);
        cloImg.setImgUrl("G:\\CODE\\software\\GitHub\\repository\\system\\wuzhi\\src\\main\\resources\\img\\icon\\QQ图片20201112153119.jpg");
        cloImg.setImgType("icon");
        int result= cloImgMapper.insert(cloImg);
        System.out.println(result);
        System.out.println(cloImg);
    }
    @Test
    public void img(){
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        String sava_path=projectPath+"\\src\\main\\resources\\img";
        QueryWrapper<CloImg> wrapper = new QueryWrapper<>();
        int clo_typs_id =5;
        String img_type="上衣";
        wrapper.eq("clo_typs_id",clo_typs_id)
                .eq("img_type",img_type);
        List<CloImg> cloImgs=cloImgMapper.selectList(wrapper);
        if(cloImgs.size()!=0){
            CloImg cloImg=cloImgs.get(0);
            String path = sava_path+"\\"+cloImg.getImgUrl();
            System.out.println(path);
        }
        else {
            System.out.println("无查找结果");
        }

    }
    @Test
    public void testCHANGDU(){
        updateStock updateStock = new updateStock();
//        updateStock.update(stockMapper,clothingMapper);
        updateStock.addStock(stockMapper,clothingMapper);
    }
    @Test
    public void testStockAdd(){
        Stock stock=new Stock();
        stock.setCloTypeId(12325);
        stockMapper.insert(stock);
    }
    @Test
    public void noticeAdd(){
        Notice notice = new Notice();
        notice.setText("0- ==========================文件生成完成！！！==========================");
        notice.setTime(new Date());
        notice.setTitle("测试测试2");
        noticeMapper.insert(notice);
    }
    @Test
    public void noticeGetNew(){
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper
                .orderByDesc("time")
                .last("limit "+1);
        List<Notice> notices= noticeMapper.selectList(wrapper);
        for (Notice n:notices){
            System.out.println(n);
        }
    }
    @Test
    public void getYueZhuCeLiang(){
        //获取时间
        int thisYear=2020;
        int thisMonth=11;
        String monthStart=thisYear+"-"+thisMonth+"-01";
        String monthEnd=thisYear+"-"+thisMonth+"-30";
        //获取当前月份的所有注册用户数量
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper
                .between("time",monthStart,monthEnd);
        List<User> users=userMapper.selectList(wrapper);
        int num=users.size();
        System.out.println(num);
    }
    @Test
    public void getYue(){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper
                .between("time","2020-11-01","2020-11-30");
        List<User> users=userMapper.selectList(wrapper);
        for (User u:users) {
            System.out.println(u);
        }
    }
    @Test
    public void add(){
        int num=3;
        String name="dress3";
        int deposit=500;
        int rent=10;
        int cost=500;
        int store_id=22222;
        int clo_type_id=13;
            addClothing addClothing1 = new addClothing();
            addClothing1.add(cloTypeMapper,clothingMapper,name,deposit,rent,cost,store_id,clo_type_id,3);
            //更新服装类型
            HashMap<String, Object> map = new HashMap<>();
            map.put("id",clo_type_id);
            List<CloType> users = cloTypeMapper.selectByMap(map);
            System.out.println(users.size());
            if (users.size()==0){
                CloType cloType = new CloType();
                cloType.setId(clo_type_id);
                cloTypeMapper.insert(cloType);
            }
        updateStock updateStock1 = new updateStock();//刷新一下库存
        updateStock1.update(stockMapper,clothingMapper);
        updateStock1.addStock(stockMapper,clothingMapper);
    }
    @Test
    public void DueDate(){
        RentList rentList = rentListMapper.selectById(1);
        java.text.Format formatter=new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date=rentList.getTime();
        int deposit = rentList.getDeposit();
        int rent = rentList.getRent();
        int Days = deposit/rent;
        long afterTime=(date.getTime()/1000)+60*60*24*Days;
        date.setTime(afterTime*1000);
        String afterDate=formatter.format(date);
        System.out.println(afterDate);
    }
    @Test
    public  void test(){
        int thisUserId=3;
//        QueryWrapper<StoreInfo> wrapper = new QueryWrapper<>();
//        wrapper
//                .eq("id",thisUserId)
//                .select("id");
//        List<StoreInfo> storeInfos = storeInfoMapper.selectList(wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",thisUserId);
        List<StoreInfo> storeInfos = storeInfoMapper.selectByMap(map);
        System.out.println(storeInfos);
        if(storeInfos.size() != 0 ){
            System.out.println("商户已存在");
        }else {
            StoreInfo storeInfo = new StoreInfo();
            storeInfo.setId(thisUserId);
            storeInfo.setAddress("地球");
            storeInfoMapper.insert(storeInfo);
        }
    }
    @Test
    public void testStarAdd(){
        int storeId=1;
        int clo_type_id=11111;
        //获取当前用户ID
        int userId=1;
        //先判断是否已经存在
        QueryWrapper<StarList> wrapper=new QueryWrapper<>();
        wrapper
                .eq("clo_type_id",clo_type_id)
                .eq("store_id",storeId);
        List<StarList> starLists=starListMapper.selectList(wrapper);
        if (starLists.size()!=0){
            System.out.println("该商品已经收藏，请勿重复收藏");
        }else {
            //添加
            StarList starList = new StarList();
            starList.setUserId(userId);
            starList.setCloTypeId(clo_type_id);
            starList.setStoreId(storeId);
            //根据商户ID和服装编号查出单价和总价
            HashMap<String, Object> map = new HashMap<>();
            map.put("store_id",storeId);
            map.put("clo_type_id",clo_type_id);
            List<Clothing> clothing = clothingMapper.selectByMap(map);
            int cost=clothing.get(0).getCost();
            starList.setCost(cost);
            starListMapper.insert(starList);
        }
    }
    @Test
    public void insqlTest(){
        QueryWrapper<Clothing> wrapper=new QueryWrapper<>();
        wrapper.select("distinct clo_type_id");
        List<Clothing> clothing=clothingMapper.selectList(wrapper);
        clothing.forEach(System.out::println);
    }
    @Test
    public void getStar() {
        //获取当前用户ID
        int userId = 1;
        QueryWrapper<StarList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("user_id", userId);
        List<StarList> starLists = starListMapper.selectList(wrapper);
        starLists.forEach(System.out::println);
    }
    @Test
    public  void clotest() {
        QueryWrapper<Clothing> wrapper =new QueryWrapper<>();
        wrapper
                .eq("store_id",1)
                .select("distinct store_id,clo_type_id");

        List<Clothing> clothing = clothingMapper.selectList(wrapper);

    }
    @Test
    public void buyTest(){
        int num=1;
        int clo_type_id=11111;
        int storeId=1;
        //刷新库存
        updateStock updateStock = new updateStock();
        updateStock.update(stockMapper,clothingMapper);
        //确定衣服余量
        buy buy1 = new buy();
        String result1=buy1.canItBuy(num,clo_type_id,storeId,stockMapper,clothingMapper);
        if (result1!="够了"){
            System.out.println(result1);
        }else {
            int customerId=1;
            //第一次写入，并生成一个id
            String resulut2=buy1.buy(customerId,clo_type_id,num,storeId,stockMapper,clothingMapper,storeInfoMapper,customerInfoMapper,sellListMapper);
            System.out.println(resulut2);
        }

    }
    @Test
    public void findAllclo(){
        List<Clothing> clothings = clothingMapper.selectList(null);
        System.out.println(clothings);
    }
    @Test
    public void testDeliverGoods(){
        int sellListId=1;
        SellList sellList= sellListMapper.selectById(sellListId);
        sellList.setState("已发货");
        sellListMapper.updateById(sellList);
        System.out.println("编号： "+sellList.getId()+" 的订单已发货");
    }
    @Test
    public void imgGetAll(){
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        String getPath=projectPath+"\\src\\main\\resources\\img";
        //type
        String type="test";
        //遍历服装（根据商家和type
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .select("distinct store_id");
        List<Object> storeIdList1= clothingMapper.selectObjs(wrapper);
        //Arr
        ArrayList<Integer> storeIdArr=new ArrayList<>();
        ArrayList<Integer> cloTypeArr=new ArrayList<>();
        ArrayList<Integer> cloIdArr=new ArrayList<>();
        ArrayList<String> imgPathArr=new ArrayList<>();
        HashMap<Integer,String> imgPathMap = new HashMap<>();
        for (Object sil:storeIdList1) {
            storeIdArr.add(Integer.parseInt(sil.toString()));
        }//获得所有商家的ID
        for (int sia:storeIdArr) {
            QueryWrapper<Clothing> wrapper1 = new QueryWrapper<>();
            wrapper1
                    .eq("store_id",sia)
                    .select("distinct clo_type_id");
            List<Object> cloTypeList= clothingMapper.selectObjs(wrapper1);//此时是获得了所有商户下所有的typeId
            for (Object cil:cloTypeList) {
                cloTypeArr.add(Integer.parseInt(cil.toString()));
            }//获得所有衣服的type
            for (int cta:cloTypeArr) {
                System.out.println("store_id:"+sia+"  clo_type_id"+cta);
                QueryWrapper<Clothing> wrapper2 = new QueryWrapper<>();
                wrapper2
                        .eq("store_id",sia)
                        .eq("clo_type_id",cta);
                List<Clothing> clothingList= clothingMapper.selectList(wrapper2);//此时是获得了所有商户下的衣服
                if (clothingList.size()==0){continue;}else {
                    cloIdArr.add(clothingList.get(0).getId());
                }
            }
        }
        System.out.println("需要查图片的衣服的ID "+cloIdArr);
        for (int i:cloIdArr) {//此时获取了所有衣服ID，现在要给每一个ID获取所有图片
            QueryWrapper<CloImg> wrapper3 = new QueryWrapper<>();
            wrapper3
                    .eq("clo_id",i)
                    .eq("img_type",type);
            List<CloImg> cloImgList1= cloImgMapper.selectList(wrapper3);//此时是获得了该ID下的所有衣服
            if (cloImgList1.size()==0){
                System.out.println("查不出来  "+i);
                continue;
            }else {
                for (CloImg cI:cloImgList1){
                    imgPathMap.put(i,getPath+"\\"+cI.getImgUrl());
                    imgPathArr.add(getPath+"\\"+cI.getImgUrl());
                    System.out.println("查出一个图片： "+cI);
                }

            }
        }
        if (imgPathMap.size()==0){
            System.out.println("没有相应图片！");
        }else {
            System.out.println(imgPathMap);
        }
        }
    @Test
    public void updataByIdtest(){
        int storeid=9;
        int cloTypeId=11111;
        //修改所有符合条件的衣服
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeid)
                .eq("clo_type_id",cloTypeId);
        List<Clothing> clothingList= clothingMapper.selectList(wrapper);//此时是获得了所有商户下所有的typeId
        for (Clothing c:clothingList){
            c.setImgUrl("test\\76094b36acaf2eddf4686fb2831001e9380193cb.jpg");
            clothingMapper.updateById(c);
        }
    }
    @Test
    public void imgGetAll2(){//首页获取图片
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        String sava_path=projectPath+"\\src\\main\\resources\\img";
        int clo_type_id=11111;
        int store_id=9;
        String url;
        String type="test";
        //
        QueryWrapper<CloImg> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",store_id)
                .eq("img_type",type)
                .eq("clo_type_id",clo_type_id);
        List<CloImg> cloImgList= cloImgMapper.selectList(wrapper);//此时是获得了所有商户下所有的typeId
        for(CloImg c:cloImgList){
            url=c.getImgUrl();
            c.setImgUrl(sava_path+"\\"+url);
        }
        System.out.println(cloImgList);
    }

    @Test
    public void cloGetAll2(){//首页获取图片
        ArrayList<String> imgUrl=new ArrayList<>();
        String projectPath = System.getProperty("user.dir");//获取当前项目路径
        String sava_path=projectPath+"\\src\\main\\resources\\img";
        String url=null;

        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .groupBy("store_id","clo_type_id")
                .isNotNull("img_id")
                .orderByDesc("img_id");
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        clothingList.forEach(System.out::println);
        //按照img_id获取图片
//        for (Clothing c:clothingList){
//            CloImg cloImg=cloImgMapper.selectById(c.getImgId());
//            url =cloImg.getImgUrl();
//            imgUrl.add(sava_path+"\\"+url);
//        }
//        System.out.println(imgUrl);
    }

    @Test
    public void StatisTest(){
        int Eyear= 2021;
        int Smonth = 1;
        int Emonth = 3;
        String NmonthStart=Eyear+"-"+Smonth+"-01";
        String NmonthEnd=Eyear+"-"+Emonth+"-30";
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",1)
//                    .between("time",monthStart,monthEnd)
                .between("time",NmonthStart,NmonthEnd);
        List<SellList> sellLists = sellListMapper.selectList(wrapper);
    }
    @Test
    public void getAllSellAmoney(){
        int amoney=0;
        int storeId=22222;
        QueryWrapper<SellList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .eq("state","已发货");
        List<SellList> sellLists= sellListMapper.selectList(wrapper);
        for (SellList s:sellLists){
            amoney=amoney+s.getAmoney();
        }
        System.out.println(amoney);
    }
    @Test
    public void getAllrentAmoney(){
        int storeId=1;
        QueryWrapper<RentList> wrapper = new QueryWrapper<>();
        wrapper
                .eq("store_id",storeId)
                .eq("state","已归还");
        List<RentList> rentLists= rentListMapper.selectList(wrapper);
            Date bigData=new Date();
            Date smData;
            int bwtTime=0;
        for (RentList r:rentLists){
            smData=r.getTime();
            Calendar cal = Calendar.getInstance();
            //处理
            cal.setTime(smData);
            long time1=cal.getTimeInMillis();
            cal.setTime(bigData);
            long time2=cal.getTimeInMillis();
            long betweenTime=(time2-time1)/(1000*3600*24);
            bwtTime= Integer.parseInt(String.valueOf(betweenTime));
            System.out.println("bwtTime  "+bwtTime);
        }
    }
    @Test
    public void imgIconTest(){
        String path="icon";
        if (path=="icon"){
            int storeid=22226;
            int cloTypeId=1;
            QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
            wrapper
                    .eq("store_id",storeid)
                    .eq("clo_type_id",cloTypeId);
            List<Clothing> clothingList= clothingMapper.selectList(wrapper);//此时是获得了所有商户下所有的typeId
            System.out.println("写入首页图");
            for (Clothing c:clothingList){
                c.setImgUrl("icon\\0ef41bd5ad6eddc47c071fc334dbb6fd52663321.jpg");
                clothingMapper.updateById(c);
            }
        }
    }
    @Test
    public void delete(){
        int id=2;
        //查询当前用户的所有内容
        int result=shoppingCartMapper.deleteById(id);
        if (result!=0){
            System.out.println("删除成功！");
            System.out.println("result"+result);
        }else {
            System.out.println("删除失败！找不到该ID！");
            System.out.println("result"+result);
        }
    }
    @Test
    public void starShow(){
        int clo_type_id=13;
        int store_id=22222;
        String sava_path="localhost:8181\\img";
        String url=null;
        QueryWrapper<Clothing> wrapper = new QueryWrapper<>();
        wrapper
                .eq("clo_type_id",clo_type_id)
                .eq("store_id",store_id)
                .eq("state","在库")
                .groupBy("store_id","clo_type_id")
                .isNotNull("img_url");
        List<Clothing> clothingList = clothingMapper.selectList(wrapper);
        Clothing c=clothingList.get(0);
        url=c.getImgUrl();
        c.setImgUrl(sava_path+"\\"+url);
        System.out.println(c);
    }

}
