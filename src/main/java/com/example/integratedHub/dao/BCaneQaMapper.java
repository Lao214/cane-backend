package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneQa;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 专家答疑问答表 Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
public interface BCaneQaMapper extends BaseMapper<BCaneQa> {

    IPage<BCaneQa> selectPage(Page<BCaneQa> pageParam,@Param("vo") BCaneQa bCaneQa);
}
