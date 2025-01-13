package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-25
 */
public interface BMessageService extends IService<BMessage> {

    IPage<BMessage> selectPage(Page<BMessage> pageParam, BMessage bMessage);
}
