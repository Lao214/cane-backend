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
 * 资源查询
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResourView对象", description="资源查询")
public class BResourView implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "用户名")
    private String username;

    //@ApiModelProperty(value = "资源key")
    private String resourKey;

    //@ApiModelProperty(value = "浏览时间")
    private Date createTime;


}
