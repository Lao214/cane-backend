package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BImg;
import com.example.integratedHub.dao.BImgMapper;
import com.example.integratedHub.service.BImgService;
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
public class BImgServiceImpl extends ServiceImpl<BImgMapper, BImg> implements BImgService {

    @Override
    public IPage<BImg> selectPage(Page<BImg> pageParam, BImg bImg) {
        return baseMapper.selectPage(pageParam,bImg);
    }
}
