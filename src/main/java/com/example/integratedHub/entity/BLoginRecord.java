package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BLoginRecord对象", description="")
public class BLoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "登录IP")
    private String loginIp;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    private String  route;


}
