package com.example.integratedHub.entity.statisticsVo;/*
 *@title loginRecordEveryWeek
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/8/29 10:35
 */

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginRecordEveryWeek {

    private String day;

    private String weekdayName;

    private Integer count;

    // 将 String 类型日期转换为 LocalDate
    public LocalDate getDateAsLocalDate() {
        return LocalDate.parse(day);
    }

    // 默认构造函数
    public LoginRecordEveryWeek() {

    }


    public LoginRecordEveryWeek(String day, int count, String weekdayName) {
        this.day = day;
        this.count = count;
        this.weekdayName = weekdayName;
    }
}
