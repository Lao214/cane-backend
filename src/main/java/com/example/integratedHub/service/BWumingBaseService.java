package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BWumingBase;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 苏运浩
 * @since 2025-04-14
 */
public interface BWumingBaseService extends IService<BWumingBase> {

    IPage<BWumingBase> selectPage(Page<BWumingBase> pageParam, BWumingBase bWumingBase);
}
