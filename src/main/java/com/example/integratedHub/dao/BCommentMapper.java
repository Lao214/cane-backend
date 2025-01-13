package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
public interface BCommentMapper extends BaseMapper<BComment> {

    List<BComment> getCommentList(@Param("resourKey") String resourKey);

    List<String> getMyCommentIds(@Param("username") String username);

    IPage<BComment> selectPage(Page<BComment> pageParam , @Param("ids") List<String> ids, @Param("username") String username);
}
