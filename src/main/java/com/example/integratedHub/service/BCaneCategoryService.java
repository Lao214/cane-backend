package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.Vo.NewQueryVo;

/**
 * <p>
 * 甘蔗分类表 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
public interface BCaneCategoryService extends IService<BCaneCategory> {

    IPage<BCaneCategory> selectPage(Page<BCaneCategory> pageParam, NewQueryVo newQueryVo);
}
