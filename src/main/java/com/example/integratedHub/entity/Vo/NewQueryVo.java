package com.example.integratedHub.entity.Vo;/*
 *@title NewQueryVo
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/24 15:57
 */

import lombok.Data;

@Data
public class NewQueryVo {

    private String tabs;

    private String title;

    private String beginStr;

    private String endStr;

    private String keyword;

    private String type;

    private String isRead;

    private Integer categoryLevel;
}
