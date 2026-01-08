package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BLoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.BMessage;
import com.example.integratedHub.entity.statisticsVo.LoginRecordEveryWeek;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2024-08-16
 */
public interface BLoginRecordMapper extends BaseMapper<BLoginRecord> {


    IPage<BLoginRecord> selectPage(Page<BLoginRecord> pageParam,@Param("vo") BLoginRecord vo);

    List<LoginRecordEveryWeek> getThisWeekLoginRecord();
}
