package com.jk.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Tree {

    private Integer id;

    private String  text;

    private String  url;

    private Integer  pid;

    private List<Tree> children;
}
