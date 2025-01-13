package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCarousel;
import com.example.integratedHub.entity.BResour;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.Vo.CountEveryTypeVo;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.ResourQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
public interface BResourMapper extends BaseMapper<BResour> {


    IPage<BResour> selectPage(Page<BResour> pageParam, @Param("vo") NewQueryVo newQueryVo);

    IPage<BResour> selectPageList(Page<BResour> pageParam, @Param("vo") ResourQueryVo resourQueryVo);

    CountEveryTypeVo countEveryType(@Param("vo") ResourQueryVo resourQueryVo);
}
