package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResour;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.Vo.CountEveryTypeVo;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.ResourQueryVo;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
public interface BResourService extends IService<BResour> {

    IPage<BResour> selectPage(Page<BResour> pageParam, NewQueryVo newQueryVo);

    IPage<BResour> selectPage(Page<BResour> pageParam, ResourQueryVo resourQueryVo);

    CountEveryTypeVo countEveryType(ResourQueryVo resourQueryVo);
}
