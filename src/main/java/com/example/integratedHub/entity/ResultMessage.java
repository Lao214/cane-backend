package com.example.integratedHub.entity;/*
 *@title ResultMessage
 *@description
 *@author echoes
 *@version 1.0
 *@create 2025/2/8 15:12
 */

import lombok.Data;

@Data
public class ResultMessage {

    private boolean system;

    private String fromName;

    private Object message;
}
