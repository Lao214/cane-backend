package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BCaneSensitivity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 敏感性指标数据库 服务类
 * </p>
 *
 * @author 苏运浩
 * @since 2025-03-19
 */
public interface BCaneSensitivityService extends IService<BCaneSensitivity> {

    IPage<BCaneSensitivity> selectPage(Page<BCaneSensitivity> pageParam, BCaneSensitivity bCane);
}
