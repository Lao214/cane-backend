package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneQa;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 专家答疑问答表 服务类
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
public interface BCaneQaService extends IService<BCaneQa> {

    IPage<BCaneQa> selectPage(Page<BCaneQa> pageParam, BCaneQa bCaneQa);
}
