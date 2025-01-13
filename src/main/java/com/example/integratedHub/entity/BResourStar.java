package com.example.integratedHub.entity;

import java.io.Serializable;
import java.util.Date;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收藏夹
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResourStar对象", description="收藏夹")
public class BResourStar implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "收藏的资源的key")
    private String starResourKey;

    //@ApiModelProperty(value = "收藏的文件夹的key")
    private String starFolderKey;

    //@ApiModelProperty(value = "收藏者")
    private String username;

    //@ApiModelProperty(value = "收藏时间")
    private Date createTime;

    @TableField(exist = false)
    private String resourName;

    @TableField(exist = false)
    private String resourType;

    @TableField(exist = false)
    private String resourFirstType;

    @TableField(exist = false)
    private String resourIcon;

}
