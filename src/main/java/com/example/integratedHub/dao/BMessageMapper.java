package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2024-08-25
 */
public interface BMessageMapper extends BaseMapper<BMessage> {


    IPage<BMessage> selectPage(Page<BMessage> pageParam,@Param("vo") BMessage bMessage);
}
