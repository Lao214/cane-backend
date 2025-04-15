package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BFBase;
import com.example.integratedHub.dao.BFBaseMapper;
import com.example.integratedHub.service.BFBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2025-04-14
 */
@Service
public class BFBaseServiceImpl extends ServiceImpl<BFBaseMapper, BFBase> implements BFBaseService {

    @Override
    public IPage<BFBase> selectPage(Page<BFBase> pageParam, BFBase bfBase) {
        return baseMapper.selectPage(pageParam,bfBase);
    }
}
