package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 专家答疑问答表
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BCaneQa对象", description="专家答疑问答表")
public class BCaneQa implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "问题描述")
    private String questionDesc;

    //@ApiModelProperty(value = "问题回复")
    private String questionAnswer;

    //@ApiModelProperty(value = "提问时间")
    private Date askTime;

    //@ApiModelProperty(value = "提问人")
    private String askUser;

    private String askAvatar;

    //@ApiModelProperty(value = "提问人姓名")
    private String askNickname;

    //@ApiModelProperty(value = "回答时间")
    private Date answerTime;

    //@ApiModelProperty(value = "回答人")
    private String answerUser;

    //@ApiModelProperty(value = "回答人姓名")
    private String answerNickname;

    private String answerAvatar;

    private String questionTitle;

    private String isAnswered;

    @TableField(exist = false)
    private String keyword;


}
