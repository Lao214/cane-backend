package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCarousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.BContact;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 公告走马灯 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-06
 */
public interface BCarouselMapper extends BaseMapper<BCarousel> {
    IPage<BCarousel> selectPage(Page<BCarousel> pageParam, @Param("vo") NewQueryVo newQueryVo);
}
