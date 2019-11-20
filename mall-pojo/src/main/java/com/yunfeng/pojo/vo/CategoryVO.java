package com.yunfeng.pojo.vo;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-20
 */

import lombok.Data;

import java.util.List;

/**
 * 二级分类vo
 */
@Data
public class CategoryVO {

    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    // 三级分类vo list
    private List<SubCategoryVO> subCatList;
}
