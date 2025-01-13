package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

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
//@ApiModel(value="BComment对象", description="")
public class BComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "所属用户")
    private String username;

    //@ApiModelProperty(value = "评论内容")
    private String comment;

    //@ApiModelProperty(value = "所属资源")
    private String resourKey;

    //@ApiModelProperty(value = "评论的类型（暂无）")
    private String commentType;

    //@ApiModelProperty(value = "父级评论ID")
    private Long parentId;

    //@ApiModelProperty(value = "顶级评论ID")
    private Long rootCommentId;

    //@ApiModelProperty(value = "是否删除")
    private String isDeleted;

    //@ApiModelProperty(value = "评论被点赞次数")
    private Integer commentLikeCount;

    //@ApiModelProperty(value = "评论时间")
    private Date createTime;

    //@ApiModelProperty(value = "评论图片")
    private String imagesUrls;

    private Integer isRead;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private String nickname;

    @TableField(exist = false)
    private String parentUsername;

    @TableField(exist = false)
    private String parentNickname;

    @TableField(exist = false)
    private List<BComment> replies = new ArrayList<>();


}
