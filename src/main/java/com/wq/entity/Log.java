package com.wq.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("操作模块")
    private String operModule;

    @ApiModelProperty("操作描述")
    private String operDesc;

    @ApiModelProperty("操作类型")
    private String operType;

    @ApiModelProperty("创建日期")
    private Date createDate;


}
