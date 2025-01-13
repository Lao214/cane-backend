package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BNews;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.UserQueryVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-24
 */
public interface BNewsService extends IService<BNews> {

    IPage<BNews> selectPage(Page<BNews> pageParam, NewQueryVo newQueryVo);
}
