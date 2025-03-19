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
 * 敏感性指标数据库
 * </p>
 *
 * @author 劳威锟
 * @since 2025-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BCaneSensitivity对象", description="敏感性指标数据库")
public class BCaneSensitivity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    //@ApiModelProperty(value = "品种名称")
    private String caneName;

    //@ApiModelProperty(value = "品种抗寒/旱/黑穗病级别")
    private String resistanceLevel;

    //@ApiModelProperty(value = "毛状体")
    private String trichome;

    //@ApiModelProperty(value = "芽型")
    private String budType;

    //@ApiModelProperty(value = "茎")
    private String stem;

    //@ApiModelProperty(value = "叶鞘")
    private String leafSheath;

    //@ApiModelProperty(value = "表皮细胞")
    private String epidermalCells;

    //@ApiModelProperty(value = "楚质层、角质层")
    private String cuticleStratumCorneum;

    //@ApiModelProperty(value = "硅细胞")
    private String siliconCell;

    //@ApiModelProperty(value = "栓细胞")
    private String thrombocytes;

    //@ApiModelProperty(value = "气孔器")
    private String stomata;

    //@ApiModelProperty(value = "叶绿体")
    private String chloroplast;

    //@ApiModelProperty(value = "线粒体")
    private String mitochondria;

    //@ApiModelProperty(value = "抗逆性化学物质")
    private String stressResistantChemicals;

    //@ApiModelProperty(value = "抗逆性酶")
    private String stressResistanceEnzyme;

    //@ApiModelProperty(value = "相关基因")
    private String relatedGenes;

    //@ApiModelProperty(value = "调控方案")
    private String controlPlan;

    //@ApiModelProperty(value = "参考文献")
    private String references;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    private Integer viewCount;

    @TableField(exist = false)
    private String label;

    @TableField(exist = false)
    private String value;

    @TableField(exist = false)
    private String keyword;

    @TableField(exist = false)
    private String sortType;


}
