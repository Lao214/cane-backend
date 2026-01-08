package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author 苏运浩
 * @since 2025-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
////@ApiModel(value="BFBase对象", description="")
public class BFBase implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "基地名")
    private String baseName;

    //@ApiModelProperty(value = "品种名")
    private String varietyName;

    //@ApiModelProperty(value = "测定日期")
    private String measurementDate;

    private String yangPlant;

    private String yangFang;

    //@ApiModelProperty(value = "株高编号")
    private String zhuGaoNum;

    private String zhuGao;

    //@ApiModelProperty(value = "平均株高（10株）")
    private String zhuGaoAve10;

    //@ApiModelProperty(value = "茎径")
    private String stemDiameter;

    //@ApiModelProperty(value = "平均茎径（10株）")
    private String stemDiameterAve10;

    //@ApiModelProperty(value = "垂度")
    private String chui;

    //@ApiModelProperty(value = "LAI")
    private String lai;

    private String laiAve;

    private String mta;

    private String mtaAve;

    private String ylshl;

    private String ylshlAve;

    //@ApiModelProperty(value = "鲜重三株")
    private String xianzhong3;

    private String xianzhongAve;

    private String ganzhong3;

    private String ganzhongAve;

    private String miaoshu;

    private String miaoshuAve;

    @TableField(exist = false)
    private String keyword;
}
