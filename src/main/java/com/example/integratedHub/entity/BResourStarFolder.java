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
 * 资源收藏文件夹表
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResourStarFolder对象", description="资源收藏文件夹表")
public class BResourStarFolder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "文件夹key")
    private String starFolderKey;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "文件夹名称")
    private String starFolderName;

    //@ApiModelProperty(value = "排序")
    private Integer sortNum;

    //@ApiModelProperty(value = "收藏夹里资源的数量")
    private Integer starCount;

    //@ApiModelProperty(value = "是否公开")
    private String isPublic;


}
