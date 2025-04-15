package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BMessage;
import com.example.integratedHub.entity.BWumingBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2025-04-14
 */
public interface BWumingBaseMapper extends BaseMapper<BWumingBase> {

    IPage<BWumingBase> selectPage(Page<BWumingBase> pageParam, @Param("vo") BWumingBase vo);
}
