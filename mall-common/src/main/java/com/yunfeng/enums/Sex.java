package com.yunfeng.enums;

import lombok.AllArgsConstructor;

/**
 * <p>
 * @Desc 性别 枚举
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-14
 */
@AllArgsConstructor
public enum Sex {
    WOMAN(0, "女"),
    MAN(1, "男"),
    SECRET(2, "保密");

    public final Integer type;
    public final String value;


}
