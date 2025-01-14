package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 甘蔗分类表
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BCaneCategory对象", description="甘蔗分类表")
public class BCaneCategory implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "分类名称")
    private String categoryName;

    //@ApiModelProperty(value = "父本ID")
    private Long parentId;

    //@ApiModelProperty(value = "分类描述")
    private String description;

    //@ApiModelProperty(value = "图片地址")
    private String imagePath;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //@ApiModelProperty(value = "创建人")
    private String createBy;

    //@ApiModelProperty(value = "更新人")
    private String updateBy;

    //@ApiModelProperty(value = "排序")
    private Integer sort;

    //@ApiModelProperty(value = "状态")
    private String status;

    //@ApiModelProperty(value = "分类级别")
    private Integer categoryLevel;

    @TableField(exist = false)
    private List<BCaneCategory> children;
}
