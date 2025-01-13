package com.example.integratedHub.entity.Vo;/*
 *@title CountEveryTypeVo
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/8/13 10:13
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class CountEveryTypeVo implements Serializable {


    // 选的数量
    private Integer selectCount;

    // 育的数量
    private Integer educateCount;

    // 用的数量
    private Integer useCount;

    // 留的数量
    private Integer keepCount;

}
