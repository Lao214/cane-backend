package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author 苏运浩
 * @since 2024-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BMessage对象", description="消息表")
public class BMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "标题")
    private String msgTitle;

    //@ApiModelProperty(value = "内容")
    private String msgContent;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "是否已通知")
    private Integer isNotify;

    //@ApiModelProperty(value = "是否已读")
    private Integer isRead;

    //@ApiModelProperty(value = "阅读时间")
    private String readTime;

    //@ApiModelProperty(value = "副标题")
    private String msgSub;

    //@ApiModelProperty(value = "点击跳转路由")
    private String confirmRoute;

    //@ApiModelProperty(value = "发送者")
    private String sender;

    //@ApiModelProperty(value = "接收者")
    private String receiver;

    //@ApiModelProperty(value = "消息类型")
    private Integer msgType;

    //@ApiModelProperty(value = "发送者名称")
    private String senderName;


}
