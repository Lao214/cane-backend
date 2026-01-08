package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("b_wuming_base") // 替换为实际表名
public class BWumingBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "基地名称")
    private String baseName;

    //@ApiModelProperty(value = "品种名")
    private String varietyName;

    //@ApiModelProperty(value = "测定日期")
    private String measurementDate;

    //@ApiModelProperty(value = "样地")
    private String samplePlot;

    //@ApiModelProperty(value = "样方")
    private String sampleFang;

    //@ApiModelProperty(value = "土壤含水量% 鲜重 0-10cm")
    private String waterContentXian10cm;

    //@ApiModelProperty(value = "土壤含水量% 干重 0-10cm")
    private String waterContentGan10cm;

    //@ApiModelProperty(value = "土壤含水量分析基% 0-10cm")
    private String waterContentFen10cm;

    //@ApiModelProperty(value = "土壤平均含水量分析基% 0-10cm")
    private String waterContentFenAverage10cm;

    //@ApiModelProperty(value = "土壤含水量干基% 0-10cm")
    private String waterContentGanji10cm;

    //@ApiModelProperty(value = "土壤平均含水量干基% 0-10cm")
    private String waterContentGanjiAverage10cm;

    //@ApiModelProperty(value = "土壤含水量% 鲜重10-20cm")
    private String waterContentXian20cm;

    //@ApiModelProperty(value = "土壤含水量% 干重10-20cm")
    private String waterContentGan20cm;

    //@ApiModelProperty(value = "土壤含水量分析基% 10-20cm")
    private String waterContentFen20cm;

    //@ApiModelProperty(value = "土壤平均含水量分析基% 10-20cm")
    private String waterContentFenAverage20cm;

    //@ApiModelProperty(value = "土壤含水量干基% 10-20cm")
    private String waterContentGanji20cm;

    //@ApiModelProperty(value = "土壤含水量 0-10cm")
    private String waterContent10cm;

    //@ApiModelProperty(value = "土壤含水量 10-20cm")
    private String waterContent20cm;

    //@ApiModelProperty(value = "土壤指标测定（有机质）空白对照40.035 FeSO4标准溶液浓度0.098取样重量（g）0-10cm")
    private String gFeso410cm;

    //@ApiModelProperty(value = "土壤指标测定（有机质）空白对照40.035FeSO4标准溶液浓度0.098滴定体积（ml）0-10cm")
    private String mlFeso410cm;

    //@ApiModelProperty(value = "土壤指标测定（有机质）空白对照40.035 FeSO4标准溶液浓度0.098 OM(土壤有机质含量g/kg）0-10cm")
    private String gkgFeso410cm;

    //@ApiModelProperty(value = "土壤指标测定（有机质）空白对照40.035FeSO4标准溶液浓度0.098取样重量（g）10-20cm")
    private String gFeso420cm;

    //@ApiModelProperty(value = "土壤指标测定（有机质）空白对照40.035 FeSO4标准溶液浓度0.098滴定体积（ml）10-20cm")
    private String mlFeso420cm;

    //@ApiModelProperty(value = "土壤指标测定（有机质）空白对照40.035FeSO4标准溶液浓度0.098OM(土壤有机质含量g/kg）10-20cm")
    private String gkgFeso420cm;

    //@ApiModelProperty(value = "土壤指标测定（钾）取样重量（g）0-10cm")
    private String gK10cm;

    //@ApiModelProperty(value = "土壤指标测定（钾）速效K浓度mg/l 0-10cm")
    private String mglK10cm;

    //@ApiModelProperty(value = "土壤指标测定（钾）速效K含量mg/kg0-10cm")
    private String mgkgK10cm;

    //@ApiModelProperty(value = "土壤指标测定（钾）取样重量（g）10-20cm")
    private String gK20cm;

    //@ApiModelProperty(value = "土壤指标测定（钾）速效K浓度mg/l10-20cm")
    private String mglK20cm;

    //@ApiModelProperty(value = "土壤指标测定（钾）速效K含量mg/kg 10-20cm")
    private String mgkgK20cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）取样重量（g）0-10cm注：111.112HCL浓度：0.074（mol/L）")
    private String gN210cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）滴定体积V1（ml）0-10cm")
    private String mlv1N210cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）滴定体积V2（ml）0-10cm")
    private String mlv2N210cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）滴定体积V0 0-10cm")
    private String mlv0N210cm;

    //@ApiModelProperty(value = "土壤全氮含量g/kg 0-10cm")
    private String gkgN210cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）取样重量（g）10-20cm")
    private String gN220cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）滴定体积V1（ml）10-20cm")
    private String mlv1N220cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）滴定体积V2（ml）10-20cm")
    private String mlv2N220cm;

    //@ApiModelProperty(value = "土壤指标测定（全氮）滴定体积V0 10-20cm")
    private String mlv0N220cm;

    //@ApiModelProperty(value = "土壤全氮含量g/kg 10-20cm")
    private String gkgN220cm;

    //@ApiModelProperty(value = "土壤指标测定（磷）取样重量（g）0-10cm")
    private String gP10cm;

    //@ApiModelProperty(value = "土壤指标测定（磷）空白吸光度值0-10cm")
    private String airP10cm;

    //@ApiModelProperty(value = "土壤指标测定（磷）样品吸光度值0-10cm")
    private String yangP10cm;

    //@ApiModelProperty(value = "土壤指标测定（磷）样品磷浓度（μg/ml）0-10cm")
    private String ugmlP10cm;

    private String mgkgP10cm;

    //@ApiModelProperty(value = "土壤指标测定（磷）取样重量（g）10-20cm")
    private String gP20cm;

    private String airP20cm;

    private String yangP20cm;

    private String ugmlP20cm;

    private String mgkgP20cm;

    //@ApiModelProperty(value = "平均株高")
    private String averagePlantHeight;

    //@ApiModelProperty(value = "平均茎径")
    private String averageStemDiameter;

    //@ApiModelProperty(value = "总茎重")
    private String totalStemWeight;

    //@ApiModelProperty(value = "有效茎径")
    private String numberEffectiveStems;

    //@ApiModelProperty(value = "茎鲜重")
    private String stemFreshWeight;

    //@ApiModelProperty(value = "平均茎鲜重")
    private String stemFreshWeightAve;

    //@ApiModelProperty(value = "叶绿片鲜重")
    private String gPlant;

    //@ApiModelProperty(value = "平均绿叶片鲜重")
    private String gPlantAve;

    //@ApiModelProperty(value = "绿叶叶鞘鲜重")
    private String gPlantShao;

    private String gPlantShaoAve;

    private String gPlantGanZhong;

    private String gPlantGanZhongAve;

    //@ApiModelProperty(value = "茎含水量")
    private String jingWaterContent;

    private String jingWaterContentAve;

    private String yeWaterContent;

    private String yeWaterContentAve;

    //@ApiModelProperty(value = "平均田间钻汁锤度(゜Bx)")
    private String bx;

    //@ApiModelProperty(value = "叶序长")
    private String yxc;

    //@ApiModelProperty(value = "叶序宽")
    private String yxk;

    private String ymj;

    private String ymjAve;

    //@ApiModelProperty(value = "青叶数")
    private String qys;

    private String qysAve;

    @TableField(exist = false)
    private String keyword;
}
