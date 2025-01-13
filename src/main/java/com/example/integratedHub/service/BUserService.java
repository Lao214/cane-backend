package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.Vo.UserQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 劳威锟
 * @since 2023-02-18
 */
public interface BUserService extends IService<BUser> {

    List<String> getAuthority(Long id);

    void updateByUsername(BUser bUser);

    IPage<BUser> selectPage(Page<BUser> pageParam, UserQueryVo userQueryVo);
}
