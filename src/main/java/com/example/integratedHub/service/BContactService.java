package com.example.integratedHub.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BContact;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.integratedHub.entity.Vo.NewQueryVo;

/**
 * <p>
 * 联系我们 服务类
 * </p>
 *
 * @author 苏运浩
 * @since 2024-05-29
 */
public interface BContactService extends IService<BContact> {

    IPage<BContact> selectPage(Page<BContact> pageParam, NewQueryVo newQueryVo);
}
