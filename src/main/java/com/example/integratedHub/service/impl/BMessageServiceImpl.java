package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BMessage;
import com.example.integratedHub.dao.BMessageMapper;
import com.example.integratedHub.service.BMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-25
 */
@Service
public class BMessageServiceImpl extends ServiceImpl<BMessageMapper, BMessage> implements BMessageService {

    @Override
    public IPage<BMessage> selectPage(Page<BMessage> pageParam, BMessage bMessage) {
        return baseMapper.selectPage(pageParam,bMessage);
    }
}
