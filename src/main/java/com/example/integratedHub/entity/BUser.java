package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 苏运浩
 * @since 2023-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
//(value="BUser对象", description="")
public class BUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //(value = "用户名")
    @TableField("username")
    private String username;

    //(value = "用户名")
    @TableField("realname")
    private String realname;

    //(value = "密码")
    @TableField("password")
    private String password;

    //(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @TableField("avatar")
    private String avatar;

    @TableField("factory")
    private String factory;

    @TableField("unit")
    private String unit;

    @TableField(exist = false)
    private String newPassword;

    @TableField(exist = false)
    private String route;

    private Integer isEnabled;

    //(value = "创建时间")
    private Date createTime;

    //(value = "修改时间")
    private Date updateTime;


}
