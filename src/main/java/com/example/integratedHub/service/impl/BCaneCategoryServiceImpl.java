package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneCategory;
import com.example.integratedHub.dao.BCaneCategoryMapper;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.service.BCaneCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 甘蔗分类表 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
@Service
public class BCaneCategoryServiceImpl extends ServiceImpl<BCaneCategoryMapper, BCaneCategory> implements BCaneCategoryService {

    @Override
    public IPage<BCaneCategory> selectPage(Page<BCaneCategory> pageParam, NewQueryVo newQueryVo) {
        return baseMapper.selectPage(pageParam,newQueryVo);
    }
}
