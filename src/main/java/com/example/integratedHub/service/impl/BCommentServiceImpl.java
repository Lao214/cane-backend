package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BComment;
import com.example.integratedHub.dao.BCommentMapper;
import com.example.integratedHub.service.BCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@Service
public class BCommentServiceImpl extends ServiceImpl<BCommentMapper, BComment> implements BCommentService {

    @Override
    public List<BComment> getCommentList(String resourKey) {
        return baseMapper.getCommentList(resourKey);
    }

    @Override
    public List<String> getMyCommentIds(String username) {
        return baseMapper.getMyCommentIds(username);
    }

    @Override
    public IPage<BComment> selectPage(Page<BComment> pageParam , List<String> ids, String username) {
        return baseMapper.selectPage(pageParam ,ids, username);
    }


}
