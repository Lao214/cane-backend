package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BNews;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2024-05-24
 */
public interface BNewsMapper extends BaseMapper<BNews> {

    IPage<BNews> selectPage(Page<BNews> pageParam, @Param("vo") NewQueryVo newQueryVo);

}
