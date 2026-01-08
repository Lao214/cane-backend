package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BWumingBase;
import com.example.integratedHub.dao.BWumingBaseMapper;
import com.example.integratedHub.service.BWumingBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 苏运浩
 * @since 2025-04-14
 */
@Service
public class BWumingBaseServiceImpl extends ServiceImpl<BWumingBaseMapper, BWumingBase> implements BWumingBaseService {

    @Override
    public IPage<BWumingBase> selectPage(Page<BWumingBase> pageParam, BWumingBase bWumingBase) {
        return baseMapper.selectPage(pageParam,bWumingBase);
    }
}
