package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
public interface BCommentService extends IService<BComment> {

    List<BComment> getCommentList(String resourKey);

    List<String> getMyCommentIds(String username);

    IPage<BComment> selectPage(Page<BComment> pageParam, List<String> ids, String username);
}
