package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BCaneSensitivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 敏感性指标数据库 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2025-03-19
 */
public interface BCaneSensitivityMapper extends BaseMapper<BCaneSensitivity> {

    IPage<BCaneSensitivity> selectPage(Page<BCaneSensitivity> pageParam, @Param("vo") BCaneSensitivity bCane);
}
