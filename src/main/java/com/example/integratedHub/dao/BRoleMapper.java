package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2024-05-13
 */
public interface BRoleMapper extends BaseMapper<BRole> {

    IPage<BRole> selectPage(Page<BRole> pageParam, @Param("vo") UserQueryVo userQueryVo);
}
