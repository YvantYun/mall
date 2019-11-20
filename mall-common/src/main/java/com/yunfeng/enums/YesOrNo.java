package com.yunfeng.enums;

import lombok.AllArgsConstructor;

/**
 * <p>
 * 是否枚举
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-19
 */
@AllArgsConstructor
public enum YesOrNo {

    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;
}
