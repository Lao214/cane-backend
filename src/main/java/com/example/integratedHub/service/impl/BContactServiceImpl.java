package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BContact;
import com.example.integratedHub.dao.BContactMapper;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.service.BContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联系我们 服务实现类
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-29
 */
@Service
public class BContactServiceImpl extends ServiceImpl<BContactMapper, BContact> implements BContactService {

    @Override
    public IPage<BContact> selectPage(Page<BContact> pageParam, NewQueryVo newQueryVo) {
        return baseMapper.selectPage(pageParam,newQueryVo);
    }
}
