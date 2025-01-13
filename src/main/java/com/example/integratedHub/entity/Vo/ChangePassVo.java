package com.example.integratedHub.entity.Vo;/*
 *@title ChangePassVo
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/8/21 15:39
 */


import lombok.Data;

@Data
public class ChangePassVo {

    private String oldPass;

    private String newPass;

    private String confirm;
}
