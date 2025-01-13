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
 * 联系我们
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BContact对象", description="联系我们")
public class BContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "用户账号")
    private String username;

    //@ApiModelProperty(value = "内容")
    private String contactText;

    //@ApiModelProperty(value = "厂区")
    private String factory;

    //@ApiModelProperty(value = "事业群")
    private String unit;

    //@ApiModelProperty(value = "联系方式")
    private String contactWay;

    //@ApiModelProperty(value = "是否已读('已读'，'未读')")
    private String isRead;

    private Date createTime;

    private String contactType;

    private String contactTitle;

    private Long feedback;


}
