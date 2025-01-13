package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResourStar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 收藏夹 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
public interface BResourStarMapper extends BaseMapper<BResourStar> {

    IPage<BResourStar> getStarByKey(Page<BResourStar> pageParam, @Param("key") String starFolderKey);
}
