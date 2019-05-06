package com.jk.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "t_goods")
public class Goods implements Serializable {

    private String id;
    private String name;
    private String imgsrc;
    private Integer count;

}
