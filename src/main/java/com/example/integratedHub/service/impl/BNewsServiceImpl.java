package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BNews;
import com.example.integratedHub.dao.BNewsMapper;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import com.example.integratedHub.service.BNewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 苏运浩
 * @since 2024-05-24
 */
@Service
public class BNewsServiceImpl extends ServiceImpl<BNewsMapper, BNews> implements BNewsService {

    @Override
    public IPage<BNews> selectPage(Page<BNews> pageParam, NewQueryVo userQueryVo) {
        return baseMapper.selectPage(pageParam, userQueryVo);
    }
}
