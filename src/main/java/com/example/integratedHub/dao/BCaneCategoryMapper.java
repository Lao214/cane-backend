package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 甘蔗分类表 Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
public interface BCaneCategoryMapper extends BaseMapper<BCaneCategory> {

    IPage<BCaneCategory> selectPage(Page<BCaneCategory> pageParam,@Param("vo") NewQueryVo newQueryVo);

}
