package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BUser;
import com.example.integratedHub.dao.BUserMapper;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import com.example.integratedHub.service.BUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 苏运浩
 * @since 2023-02-18
 */
@Service
public class BUserServiceImpl extends ServiceImpl<BUserMapper, BUser> implements BUserService {

    @Override
    public List<String> getAuthority(Long id) {
        return baseMapper.getAuthority(id);
    }

    @Override
    public void updateByUsername(BUser bUser) {
        baseMapper.updateByUsername(bUser);
    }

    @Override
    public IPage<BUser> selectPage(Page<BUser> pageParam, UserQueryVo userQueryVo) {
        return baseMapper.selectPage(pageParam,userQueryVo);
    }
}
