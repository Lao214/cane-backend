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
 * 甘蔗表
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BCane对象", description="甘蔗表")
public class BCane implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "甘蔗ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "甘蔗品种名称")
    private String caneName;

    //@ApiModelProperty(value = "甘蔗品种别名")
    private String caneAlias;

    //@ApiModelProperty(value = "属名")
    private String genericname;

    //@ApiModelProperty(value = "亲本信息父ID")
    private Long parentId;

    //@ApiModelProperty(value = "亲本信息母ID")
    private Long motherId;

    //@ApiModelProperty(value = "分类ID")
    private Long categoryId;

    //@ApiModelProperty(value = "熟期")
    private String ripePeriod;

    //@ApiModelProperty(value = "株高")
    private String plantHeight;

    //@ApiModelProperty(value = "茎 直径")
    private String stemDiamet;

    //@ApiModelProperty(value = "引入时间")
    private Date intorDates;

    //@ApiModelProperty(value = "引入单位")
    private String intorBusiness;

    //@ApiModelProperty(value = "抗性表现")
    private String seedHabits;

    //@ApiModelProperty(value = "主要种植地区编码")
    private String cityCodes;

    //@ApiModelProperty(value = "种质获取")
    private String germplasm;

    //@ApiModelProperty(value = "审定编号")
    private String apprcode;

    //@ApiModelProperty(value = "选育单位")
    private String breedUnits;

    //@ApiModelProperty(value = "季节")
    private String season;

    //@ApiModelProperty(value = "选育年代")
    private String breedYear;

    //@ApiModelProperty(value = "参考文献")
    private String reference;

    //@ApiModelProperty(value = "品种特性")
    private String variFeatures;

    //@ApiModelProperty(value = "种植情况")
    private String variCondition;

    //@ApiModelProperty(value = "量产表现")
    private String massProduction;

    //@ApiModelProperty(value = "栽培技术")
    private String cultivationTechniques;

    //@ApiModelProperty(value = "备注（其他）")
    private String remark;

    //@ApiModelProperty(value = "描述")
    private String description;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "更新时间")
    private Date updateTime;

    //@ApiModelProperty(value = "创建人")
    private String createBy;

    //@ApiModelProperty(value = "维护人")
    private String updateBy;

    //@ApiModelProperty(value = "状态")
    private String status;

    //@ApiModelProperty(value = "图片地址")
    private String imagePath;

    //@ApiModelProperty(value = "专家建议")
    private String expertAdvice;

    //@ApiModelProperty(value = "推荐种植的区域和季节")
    private String recommendedPlanting;

    private Integer viewCount;

    // 抗旱属性
    private String droughtResistance;

    // 抗寒属性
    private String coldResistance;

    // 抗黑穗病属性
    private String smutResistance;

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private String fatherName;

    @TableField(exist = false)
    private String motherName;

    @TableField(exist = false)
    private String label;

    @TableField(exist = false)
    private String value;

    @TableField(exist = false)
    private String keyword;

    @TableField(exist = false)
    private String sortType;

}
