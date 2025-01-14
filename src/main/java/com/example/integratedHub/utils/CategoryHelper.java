package com.example.integratedHub.utils;

import com.example.integratedHub.entity.BCaneCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryHelper {

    //构建树形结构
    public static List<BCaneCategory> bulidTree(List<BCaneCategory> sysMenuList) {
        //创建集合封装最终数据
        List<BCaneCategory> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (BCaneCategory sysMenu:sysMenuList) {
            //找到递归入口，parentid=0
            if(sysMenu.getParentId().longValue()==0) {
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    //从根节点进行递归查询，查询子节点
    // 判断 id =  parentid是否相同，如果相同是子节点，进行数据封装
    private static BCaneCategory findChildren(BCaneCategory sysMenu, List<BCaneCategory> treeNodes) {
        //数据初始化
//        sysMenu.setChildren(new ArrayList<BCourseType>());
        //遍历递归查找
        for (BCaneCategory it:treeNodes) {
            //获取当前菜单id
//            String id = sysMenu.getId();
//            long cid = Long.parseLong(id);
            //获取所有菜单parentid
//            Long parentId = it.getParentId();
            //比对
            if(sysMenu.getId()==it.getParentId()) {
                if(sysMenu.getChildren()==null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }
}
