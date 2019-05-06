package com.jk.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Document(collection = "t_good")
public class CommentsBean {

    private String id;
    private String comments;
    private String commentsName;
    private String commentsLevel;
    private String commentsStars;
    private Integer count;
    private String goodsid;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createtime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date sratrcreatetime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endcreatetime;
}
