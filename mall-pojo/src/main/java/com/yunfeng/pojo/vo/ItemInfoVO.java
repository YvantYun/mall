package com.yunfeng.pojo.vo;

import com.yunfeng.pojo.Items;
import com.yunfeng.pojo.ItemsImg;
import com.yunfeng.pojo.ItemsParam;
import com.yunfeng.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

@Data
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}