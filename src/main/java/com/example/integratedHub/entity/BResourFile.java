package com.example.integratedHub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资源对应的文件
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="BResourFile对象", description="资源对应的文件")
public class BResourFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "资源键")
    private String resourKey;

    //@ApiModelProperty(value = "资源文件类型")
    private String resourFileType;

    //@ApiModelProperty(value = "路径")
    private String resourFilePath;

    //@ApiModelProperty(value = "资源文件名称")
    private String resourFileName;

    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    //@ApiModelProperty(value = "创建者")
    private String creator;

    //@ApiModelProperty(value = "下载次数")
    private Integer downloadCount;


}
