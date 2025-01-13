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
 * 
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BNews对象", description="")
public class BNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "新闻标题")
    private String newTitle;

    //@ApiModelProperty(value = "新闻主要内容")
    private String newContent;

    //@ApiModelProperty(value = "发布时间")
    private Date createTime;
    @TableField(exist = false)
    private String createTimeStr;

    //@ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //@ApiModelProperty(value = "观看人数")
    private Integer viewCount;

    //@ApiModelProperty(value = "点赞人数")
    private Integer likeCount;

    //@ApiModelProperty(value = "相关文章")
    private String relatedArticles;

    //@ApiModelProperty(value = "文章系列")
    private String newSeries;

    //@ApiModelProperty(value = "文章标签")
    private String newTags;

    //@ApiModelProperty(value = "副标题")
    private String newSub;

    //@ApiModelProperty(value = "key")
    private String newKey;

    //@ApiModelProperty(value = "状态")
    private String status;

    //@ApiModelProperty(value = "作者")
    private String newAuthor;

    //@ApiModelProperty(value = "创建者工号")
    private String createUsername;

    //@ApiModelProperty(value = "相关资源")
    private String relatedResour;

    //@ApiModelProperty(value = "新闻类型")
    private Integer newType;

}
