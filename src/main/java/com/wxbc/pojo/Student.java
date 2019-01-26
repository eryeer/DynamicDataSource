package com.wxbc.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Student {
    private int id;
    @ApiModelProperty(value = "年龄", example = "11", required = true)
    private int age;

    @ApiModelProperty(value = "语言名", example = "eng", required = true)
    private String languageName;

    @ApiModelProperty(value = "姓名", example = "Tom", required = true)
    private String name;
}
