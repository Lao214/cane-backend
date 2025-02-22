package com.example.integratedHub.utils;/*
 *@title MessageUtils
 *@description
 *@author echoes
 *@version 1.0
 *@create 2025/2/8 15:09
 */

import com.alibaba.fastjson.JSON;
import com.example.integratedHub.entity.ResultMessage;

public class MessageUtils {

    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {

        ResultMessage result = new ResultMessage();

        result.setSystem(isSystemMessage);
        if(fromName != null) {
            result.setFromName(fromName);
        }
        result.setMessage(message);


        return JSON.toJSONString(result);
    }
}
