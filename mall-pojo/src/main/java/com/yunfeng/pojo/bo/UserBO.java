package com.yunfeng.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-14
 */
@Data
@ApiModel(value = "用户对象BO",description = "从客户端，由用户传入的数据封装在此entity种")
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "yunfeng", required = true)
    private String username;
    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;
    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = true)
    private String confirmPassword;
}
