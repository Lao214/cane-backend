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
 * @author 苏运浩
 * @since 2024-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BRole对象", description="")
public class BRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "角色名称")
    private String roleName;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
