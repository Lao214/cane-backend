package com.example.integratedHub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.dao.BUserRoleMapper;
import com.example.integratedHub.entity.BRole;
import com.example.integratedHub.dao.BRoleMapper;
import com.example.integratedHub.entity.BUserRole;
import com.example.integratedHub.entity.Vo.AssginRoleVo;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import com.example.integratedHub.service.BRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 苏运浩
 * @since 2024-05-13
 */
@Service
public class BRoleServiceImpl extends ServiceImpl<BRoleMapper, BRole> implements BRoleService {

    @Autowired
    private BUserRoleMapper bUserRoleMapper;

    @Override
    public Map<String, Object> getRolesByUserId(String userId) {
        //获取所有角色
        List<BRole> roles = baseMapper.selectList(null);
        //根据用户id查询，已经分配角色
        QueryWrapper<BUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<BUserRole> userRolesList = bUserRoleMapper.selectList(wrapper);
        //从userRoles集合获取所有角色id
        List<Long> userRoleIds = new ArrayList<>();
        for (BUserRole userRole:userRolesList) {
            Long roleId = userRole.getRoleId();
            userRoleIds.add(roleId);
        }
        //封装到map集合
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",roles);//所有角色
        returnMap.put("userRoleIds",userRoleIds);//用户分配角色id集合
        return returnMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除之前分配角色
        QueryWrapper<BUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assginRoleVo.getUserId());
        bUserRoleMapper.delete(wrapper);
        //获取所有角色id，添加角色用户关系表
        //角色id列表
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        for (String roleId:roleIdList) {
            BUserRole userRole = new BUserRole();
            userRole.setUserId(assginRoleVo.getUserId());
            userRole.setRoleId(Long.parseLong(roleId));
            bUserRoleMapper.insert(userRole);
        }
    }

    @Override
    public IPage<BRole> selectPage(Page<BRole> pageParam, UserQueryVo userQueryVo) {
        IPage<BRole> pageModel = baseMapper.selectPage(pageParam,userQueryVo);
        return pageModel;
    }
}
