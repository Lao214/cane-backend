package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResourStar;
import com.example.integratedHub.dao.BResourStarMapper;
import com.example.integratedHub.service.BResourStarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 收藏夹 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@Service
public class BResourStarServiceImpl extends ServiceImpl<BResourStarMapper, BResourStar> implements BResourStarService {

    @Override
    public IPage<BResourStar> getStarByKey(Page<BResourStar> pageParam, String starFolderKey) {
        return baseMapper.getStarByKey(pageParam,starFolderKey);
    }
}
