package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 种质区域信息表格
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BCaneArea对象", description="种质区域信息表格")
public class BCaneArea implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "区域名称")
    private String areaName;

    //@ApiModelProperty(value = "区域编码")
    private String areaCode;

    //@ApiModelProperty(value = "区域经度")
    private String areaLongitude;

    //@ApiModelProperty(value = "区域纬度")
    private String areaLatitude;


}
