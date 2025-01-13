package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.dao.BResourMapper;
import com.example.integratedHub.entity.Vo.CountEveryTypeVo;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.ResourQueryVo;
import com.example.integratedHub.service.BResourService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
@Service
public class BResourServiceImpl extends ServiceImpl<BResourMapper, BResour> implements BResourService {

    @Override
    public IPage<BResour> selectPage(Page<BResour> pageParam, NewQueryVo newQueryVo) {
        return baseMapper.selectPage(pageParam, newQueryVo);
    }

    @Override
    public IPage<BResour> selectPage(Page<BResour> pageParam, ResourQueryVo resourQueryVo) {
        return baseMapper.selectPageList(pageParam, resourQueryVo);
    }

    @Override
    public CountEveryTypeVo countEveryType(ResourQueryVo resourQueryVo) {
        return baseMapper.countEveryType(resourQueryVo);
    }
}
