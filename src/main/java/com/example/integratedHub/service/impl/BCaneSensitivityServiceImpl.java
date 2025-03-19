package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BCaneSensitivity;
import com.example.integratedHub.dao.BCaneSensitivityMapper;
import com.example.integratedHub.service.BCaneSensitivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 敏感性指标数据库 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2025-03-19
 */
@Service
public class BCaneSensitivityServiceImpl extends ServiceImpl<BCaneSensitivityMapper, BCaneSensitivity> implements BCaneSensitivityService {

    @Override
    public IPage<BCaneSensitivity> selectPage(Page<BCaneSensitivity> pageParam, BCaneSensitivity bCane) {
        return baseMapper.selectPage(pageParam,bCane);
    }
}
