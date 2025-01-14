package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.BCaneCategory;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 甘蔗表 Mapper 接口
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
public interface BCaneMapper extends BaseMapper<BCane> {


    IPage<BCane> selectPage(Page<BCane> pageParam, @Param("vo") BCane bCane);

}
