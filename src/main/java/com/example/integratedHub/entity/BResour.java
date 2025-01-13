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
 * 资源表
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResour对象", description="资源表")
public class BResour implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "资源名称")
    private String resourName;

    //@ApiModelProperty(value = "资源键")
    private String resourKey;

    //@ApiModelProperty(value = "资源简介")
    private String resourDes;

    private String resourType;

    private String resourFirstType;

    private String resourIcon;

    private String resourIllustrate;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "创建者")
    private String creator;

    //@ApiModelProperty(value = "使用次数")
    private Integer usedCount;

    //@ApiModelProperty(value = "查看次数")
    private Integer viewCount;

    //@ApiModelProperty(value = "下载总次数")
    @TableField(exist = false)
    private Integer downloadCount;

    //@ApiModelProperty(value = "下载总次数")
    private Integer likeCount;

    //@ApiModelProperty(value = "下载总次数")
    private Integer starCount;

    @TableField(exist = false)
    private String isNew;


}
