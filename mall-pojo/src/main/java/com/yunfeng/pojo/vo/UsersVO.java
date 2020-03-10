package com.yunfeng.pojo.vo;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2020-01-17
 */

@Data
public class UsersVO {

    private String id;

    private String username;

    private String nickname;

    private String face;

    /**
     * 1男 0女 2保密
     */
    private Integer sex;

    private String userUniqueToken;

}
