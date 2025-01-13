package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2023-02-18
 */
public interface BUserMapper extends BaseMapper<BUser> {

    List<String> getAuthority(@Param("id") Long id);

    void updateByUsername(@Param("vo")BUser bUser);

    IPage<BUser> selectPage(Page<BUser> pageParam, @Param("vo") UserQueryVo sysUserQueryVo);
}
