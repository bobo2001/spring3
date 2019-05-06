package com.jk.dao;

import com.jk.pojo.Tree;
import com.jk.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodDao {
    @Select("select * from t_tree where pid = #{value}")
    List<Tree> findMyTreeListByPid(Integer id);

    @Select("select * from jk_user where account = #{value}")
    User findAccount(String account);

    @Select("select * from jk_user u where u.account =#{value}")
    User findUserByLoginNumber(String account);
}
