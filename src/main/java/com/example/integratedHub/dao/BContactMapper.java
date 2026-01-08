package com.example.integratedHub.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BContact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.integratedHub.entity.BNews;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 联系我们 Mapper 接口
 * </p>
 *
 * @author 苏运浩
 * @since 2024-05-29
 */
public interface BContactMapper extends BaseMapper<BContact> {

    IPage<BContact> selectPage(Page<BContact> pageParam, @Param("vo") NewQueryVo newQueryVo);
}
