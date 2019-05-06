package com.jk.service;

import com.jk.pojo.CommentsBean;
import com.jk.pojo.Goods;
import com.jk.pojo.Tree;
import com.jk.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface GoodService {
    List<Tree> findMyTree();


    HashMap<String, Object> findUserPage(Integer page, Integer rows, CommentsBean comments, String goodid);

    void saveComments(CommentsBean comments);

    void updateComments(CommentsBean comments);

    HashMap<String, Object> findGoods(Integer page, Integer rows, Goods goods);

    CommentsBean updhuixian(String id);

    void adduserList(Goods goods);

    HashMap<String, Object> login(User userBean, String imgcode, HttpServletRequest request);

    String gainMessgerCode(String phoneNumber, HttpSession session);

    String messageLogin(String account, String messageCode, HttpSession session);
}
