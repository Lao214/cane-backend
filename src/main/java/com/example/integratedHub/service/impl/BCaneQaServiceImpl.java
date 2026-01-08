package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneQa;
import com.example.integratedHub.dao.BCaneQaMapper;
import com.example.integratedHub.service.BCaneQaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 专家答疑问答表 服务实现类
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
@Service
public class BCaneQaServiceImpl extends ServiceImpl<BCaneQaMapper, BCaneQa> implements BCaneQaService {

    @Override
    public IPage<BCaneQa> selectPage(Page<BCaneQa> pageParam, BCaneQa bCaneQa) {
        return baseMapper.selectPage(pageParam,bCaneQa);
    }
}
