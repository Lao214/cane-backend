package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BImg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2025-04-14
 */
public interface BImgService extends IService<BImg> {

    IPage<BImg> selectPage(Page<BImg> pageParam, BImg bImg);
}
