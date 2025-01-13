package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResourStar;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 收藏夹 服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
public interface BResourStarService extends IService<BResourStar> {

    IPage<BResourStar> getStarByKey(Page<BResourStar> pageParam, String starFolderKey);


}
