package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BNewsView对象", description="")
public class BNewsView implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "新闻的id")
    private Long newId;

    //@ApiModelProperty(value = "查看者")
    private String username;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;


}
