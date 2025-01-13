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
 * 公告走马灯
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BCarousel对象", description="公告走马灯")
public class BCarousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "banner链接")
    private String bannerImg;

    //@ApiModelProperty(value = "公告配文（最多200字符）")
    private String carouselText;

    //@ApiModelProperty(value = "公告标题（最多50字符）")
    private String carouselTitle;

    //@ApiModelProperty(value = "点击走马灯要跳转的链接")
    private String carouselUrl;

    private String carouselUrlBtn;

    //@ApiModelProperty(value = "状态")
    private String carouselStatus;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "创建者")
    private String creator;

    //@ApiModelProperty(value = "创建者姓名")
    private String creatorName;

    //@ApiModelProperty(value = "配文背景颜色")
    private String textBackgroundColor;

    //@ApiModelProperty(value = "配文字体颜色")
    private String textColor;

    private String isText;

    private String textPosition;

}
