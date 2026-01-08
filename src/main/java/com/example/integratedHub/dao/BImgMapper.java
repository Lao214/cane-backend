package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BImg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.BLoginRecord;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2025-04-14
 */
public interface BImgMapper extends BaseMapper<BImg> {

    IPage<BImg> selectPage(Page<BImg> pageParam, @Param("vo") BImg vo);
}
