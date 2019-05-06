package com.jk.controller;

import com.jk.pojo.CommentsBean;
import com.jk.pojo.Goods;
import com.jk.pojo.Tree;
import com.jk.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class GoodController {

    @Autowired
    private GoodService goodService;

    @RequestMapping("findMyTree")
    public List<Tree> findMyTree(){
        return goodService.findMyTree();
    }

    @RequestMapping("findComments")
    public HashMap<String, Object> findRolePage(String goodid,Integer page, Integer rows, CommentsBean comments){
        return goodService.findUserPage(page,rows,comments,goodid);
    }

    @RequestMapping("saveComments")
    public boolean saveComments(CommentsBean comments){
        try {
            if(comments.getId()==null || "".equals(comments.getId())){
                goodService.saveComments(comments);
                return true;
            }else{
                goodService.updateComments(comments);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("findGoods")
    public HashMap<String,Object> findGoods(Integer page, Integer rows, Goods goods){

        return goodService.findGoods(page,rows,goods);
    }

    //修改回显
    @RequestMapping("updhuixian")
    public CommentsBean updhuixian(String id) {
        CommentsBean updhuixian = goodService.updhuixian(id);
        return updhuixian;
    }
    /*商品新增*/

    @RequestMapping("adduserList")
    public boolean adduserList(Goods goods) {
        try {
            goodService.adduserList(goods);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
