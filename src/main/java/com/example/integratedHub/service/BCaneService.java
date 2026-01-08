package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 甘蔗表 服务类
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
public interface BCaneService extends IService<BCane> {

    IPage<BCane> selectPage(Page<BCane> pageParam, BCane bCane);
}
