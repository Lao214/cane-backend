package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.dao.BCaneMapper;
import com.example.integratedHub.service.BCaneService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 甘蔗表 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
@Service
public class BCaneServiceImpl extends ServiceImpl<BCaneMapper, BCane> implements BCaneService {

    @Override
    public IPage<BCane> selectPage(Page<BCane> pageParam, BCane bCane) {
        return baseMapper.selectPage(pageParam, bCane);
    }
}
