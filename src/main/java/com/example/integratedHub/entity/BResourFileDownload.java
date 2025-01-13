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
 * 资源附件下载记录表
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResourFileDownload对象", description="资源附件下载记录表")
public class BResourFileDownload implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "附件key")
    private Long resourFileId;

    //@ApiModelProperty(value = "下载者")
    private String username;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;


    @TableField(exist = false)
    private String resourKey;


}
