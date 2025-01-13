package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.statisticsVo.LoginRecordEveryWeek;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-16
 */
public interface BLoginRecordService extends IService<BLoginRecord> {

    IPage<BLoginRecord> selectPage(Page<BLoginRecord> pageParam, BLoginRecord bLoginRecord);

    List<LoginRecordEveryWeek> getThisWeekLoginRecord();
}
