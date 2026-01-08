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
 * @since 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BImg对象", description="")
public class BImg implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String imgPath;

    //@ApiModelProperty(value = "描述")
    private String imgDesc;

    private Date createTime;

    private String imgName;


}
