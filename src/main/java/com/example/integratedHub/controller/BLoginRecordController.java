package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BLoginRecord;
import com.example.integratedHub.entity.statisticsVo.LoginRecordEveryWeek;
import com.example.integratedHub.service.BLoginRecordService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 苏运浩
 * @since 2024-08-16
 */
@RestController
@RequestMapping("/loginRecord")
public class BLoginRecordController {


    @Autowired
    private BLoginRecordService bLoginRecordService;

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, BLoginRecord bLoginRecord) {
        //创建page对象
        Page<BLoginRecord> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BLoginRecord> pageModel = bLoginRecordService.selectPage(pageParam,bLoginRecord);
        return Result.success().data("data",pageModel);
    }

    @GetMapping("getThisWeekLoginRecord")
    public Result getThisWeekLoginRecord() {

        // 生成本周所有日期
        List<LocalDate> thisWeekDates = getThisWeekDates();

        // 从数据库获取登录记录
        List<LoginRecordEveryWeek> loginRecordEveryWeekList = bLoginRecordService.getThisWeekLoginRecord();

        // 将记录按日期映射
        Map<LocalDate, LoginRecordEveryWeek> recordMap = loginRecordEveryWeekList.stream()
                .collect(Collectors.toMap(record -> record.getDateAsLocalDate(), record -> record));

        // 补全没有数据的日期
        List<LoginRecordEveryWeek> completeLoginRecordList = new ArrayList<>();
        for (LocalDate date : thisWeekDates) {
            LoginRecordEveryWeek record = recordMap.get(date);
            // 添加星期几名称
            String weekdayName = getWeekdayName(date);
            if (record == null) {
                // 如果该日期没有数据，补全一条 count 为 0 的记录
                record = new LoginRecordEveryWeek(date.toString(), 0,weekdayName);
            }
            completeLoginRecordList.add(new LoginRecordEveryWeek(date.toString(), record.getCount(), weekdayName));
        }

        return Result.success().data("data",completeLoginRecordList);
    }

    private List<LocalDate> getThisWeekDates() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = startOfWeek; !date.isAfter(endOfWeek); date = date.plusDays(1)) {
            dates.add(date);
        }
        return dates;
    }

    private String getWeekdayName(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
            default:
                return "";
        }
    }

}

