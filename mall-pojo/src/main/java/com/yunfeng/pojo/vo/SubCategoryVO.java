package com.yunfeng.pojo.vo;

import lombok.Data;

/**
 * <p>
 * 三级分类vo
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-20
 */
@Data
public class SubCategoryVO {

    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

}
