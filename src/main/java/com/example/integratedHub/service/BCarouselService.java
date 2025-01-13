package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCarousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.Vo.NewQueryVo;

/**
 * <p>
 * 公告走马灯 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-06
 */
public interface BCarouselService extends IService<BCarousel> {

    IPage<BCarousel> selectPage(Page<BCarousel> pageParam, NewQueryVo newQueryVo);
}
