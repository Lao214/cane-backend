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
 * @since 2024-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResourLike对象", description="")
public class BResourLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "资源key")
    private String resourKey;

    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;


}
