package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCarousel;
import com.example.integratedHub.dao.BCarouselMapper;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.service.BCarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公告走马灯 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-06
 */
@Service
public class BCarouselServiceImpl extends ServiceImpl<BCarouselMapper, BCarousel> implements BCarouselService {

    @Override
    public IPage<BCarousel> selectPage(Page<BCarousel> pageParam, NewQueryVo newQueryVo) {
        return baseMapper.selectPage(pageParam,newQueryVo);
    }
}
