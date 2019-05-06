package com.jk.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jk.dao.GoodDao;
import com.jk.pojo.CommentsBean;
import com.jk.pojo.Goods;
import com.jk.pojo.Tree;
import com.jk.pojo.User;
import com.jk.util.HttpClientUtil;
import com.jk.util.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    GoodDao goodDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public HashMap<String, Object> findUserPage(Integer page, Integer rows, CommentsBean comments, String goodid) {
        Query query = new Query();
        HashMap<String,Object> map=new HashMap<>();
        //根据id查询
        query.addCriteria(Criteria.where("goodsid").is(goodid));
        //模糊搜索
        if(StringUtils.isNotEmpty(comments.getComments())) {
            query.addCriteria(Criteria.where("comments").regex(comments.getComments()));
        }
        //区间搜索
        if(comments.getSratrcreatetime() != null && comments.getEndcreatetime() != null) {
            query.addCriteria(Criteria.where("createtime").gte(comments.getSratrcreatetime()).lte(comments.getEndcreatetime()));
        }
        //查询数量
        long count = mongoTemplate.count(query, CommentsBean.class);
        //起始条数
        //查询数据
        Integer start =(page-1)*rows;
        query.skip(start).limit(rows);

        List<CommentsBean> list = mongoTemplate.find(query , CommentsBean.class);
        map.put("rows",list);
        return map;
    }

    @Override
    public void saveComments(CommentsBean comments) {
        CommentsBean commentsBean = new CommentsBean();
        commentsBean.setComments(comments.getComments());
        commentsBean.setCommentsLevel(comments.getCommentsLevel());
        commentsBean.setCommentsName(comments.getCommentsName());
        commentsBean.setCommentsStars(comments.getCommentsStars());
        commentsBean.setGoodsid(comments.getGoodsid());
        commentsBean.setCreatetime(new Date());
        mongoTemplate.save(commentsBean);
    }

    @Override
    public void updateComments(CommentsBean comments) {
        CommentsBean commentsBean = new CommentsBean();
        commentsBean.setId(comments.getId());
        commentsBean.setComments(comments.getComments());
        commentsBean.setCommentsLevel(comments.getCommentsLevel());
        commentsBean.setCommentsName(comments.getCommentsName());
        commentsBean.setCommentsStars(comments.getCommentsStars());
        commentsBean.setGoodsid(comments.getGoodsid());
        commentsBean.setCreatetime(comments.getCreatetime());
        mongoTemplate.save(commentsBean);
    }

    @Override
    public HashMap<String, Object> findGoods(Integer page, Integer rows, Goods goods) {
        Query query = new Query();
        HashMap<String, Object> map = new HashMap<>();
        //查询数量
        long total = mongoTemplate.count(query, Goods.class);
        //起始条数
        int start = (page - 1) * rows;
        //分页
        query.skip(start).limit(rows);
        //查询数据
        List<Goods> find2 = mongoTemplate.find(query, Goods.class);

        redisTemplate.opsForValue().set(page, find2);
        List<Goods> ob = (List<Goods>) redisTemplate.opsForValue().get(page);

        if (ob != null && !ob.toString().equals("")&&ob.size()>0) {
            map.put("rows", ob);
            map.put("total", total);
            return map;
        }

            for (int i = 0; i < find2.size(); i++) {
                Query query2 = new Query();
                //获取每一行的id
                String id = find2.get(i).getId();
                //根据id查询对应评论表的数量
                query2.addCriteria(Criteria.where("goodsid").is(id));
                Integer count2 = (int) mongoTemplate.count(query2, CommentsBean.class);
                find2.get(i).setCount(count2);
            }
            map.put("total", total);
            map.put("rows", find2);
            return map;
        }

    //回显
    @Override
    public CommentsBean updhuixian(String id) {
        // TODO Auto-generated method stub
        CommentsBean findById = mongoTemplate.findById(id, CommentsBean.class);
        return findById;
    }

    /*商品新增*/
    @Override
    public void adduserList(Goods goods) {
        mongoTemplate.save(goods);
    }

    @Override
    public HashMap<String, Object> login(User userBean, String imgcode, HttpServletRequest request) {
        HashMap<String, Object> hashMap = new HashMap<>();
        HttpSession session = request.getSession();
        User user = goodDao.findAccount(userBean.getAccount());
        if(user==null) {
            hashMap.put("code", 1);
            hashMap.put("msg", "账号错误！");
            return hashMap;
        }
        String passeword = user.getPassword();
        if(!passeword.equals(passeword)) {
            hashMap.put("code", 2);
            hashMap.put("msg", "密码错误！");
            return hashMap;
        }

        session.setAttribute(session.getId(), user);
        hashMap.put("code", 0);
        hashMap.put("msg", "登陆成功");
        return hashMap;

    }

    @Override
    public String gainMessgerCode(String phoneNumber, HttpSession session) {
        User user = goodDao.findUserByLoginNumber(phoneNumber);
        if(user==null){
            return "手机号不存在";
        }
        String url = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
        HashMap<String, Object> params = new HashMap<>();
        String accountSid = "0374867b2c1844dbbe0bf019bf0def28";
        params.put("accountSid", accountSid);//开发者主账号ID（ACCOUNT SID）
        params.put("templateid", "164547838");//短信模板ID
        //6位随机数，验证码
        int code = (int) Math.ceil(Math.random()*899999+100000);
        //String codeStr = String.valueOf(code);
        params.put("param", code);//短信模板中的变量值
        params.put("to", phoneNumber);//手机号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        params.put("timestamp", time);//时间戳
        String token = "d05d06f418974fc6aceb9233e38b7539";
        String sig = Md5Util.getMd532(accountSid+token+time);
        params.put("sig", sig);//签名
        //发送短信
        String returnStr = HttpClientUtil.post(url, params);
        JSONObject parseObject = JSON.parseObject(returnStr);
        String respCode = parseObject.getString("respCode");

        if(!respCode.equals("00000")){
            return respCode;
        }
        //存验证码
        session.setAttribute("coo",code);

        return "发送短信验证码成功";
    }

    @Override
    public String messageLogin(String account, String messageCode, HttpSession session) {
        //验证手机号是否存在
        User user = goodDao.findUserByLoginNumber(account);
        if(user==null){
            return "手机号不存在";
        }
        //验证短信验证码是否正确
        //判断key是否存在
        Object attribute = session.getAttribute("coo");

        if(attribute ==null){
            return "验证码错误";
        }
        if(!messageCode.equals(attribute.toString())){
            return "验证码错误";
        }
        //登录成功
        session.setAttribute(session.getId(), user);

        return "登录成功";
    }


    @Override
    public List<Tree> findMyTree() {
        Integer id = 0;
        List<Tree> node = getNode(id);
        return node;
    }

    private List<Tree> getNode(Integer id) {
        List<Tree> findMyTreeListByPid = goodDao.findMyTreeListByPid(id);
        for (Tree tree : findMyTreeListByPid) {

            Integer id2 = tree.getId();
            List<Tree> nodes = getNode(id2);
            tree.setChildren(nodes);
        }
        return findMyTreeListByPid;
    }
}
