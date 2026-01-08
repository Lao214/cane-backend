package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BLoginRecord;
import com.example.integratedHub.dao.BLoginRecordMapper;
import com.example.integratedHub.entity.statisticsVo.LoginRecordEveryWeek;
import com.example.integratedHub.service.BLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 苏运浩
 * @since 2024-08-16
 */
@Service
public class BLoginRecordServiceImpl extends ServiceImpl<BLoginRecordMapper, BLoginRecord> implements BLoginRecordService {

    @Override
    public IPage<BLoginRecord> selectPage(Page<BLoginRecord> pageParam, BLoginRecord bLoginRecord) {
        return baseMapper.selectPage(pageParam,bLoginRecord);
    }

    @Override
    public List<LoginRecordEveryWeek> getThisWeekLoginRecord() {
        return baseMapper.getThisWeekLoginRecord();
    }
}
