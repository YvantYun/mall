package com.yunfeng.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 最新商品vo
 */
@Data
public class NewItemsVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVO> simpleItemList;
}
