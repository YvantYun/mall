package com.yunfeng.mapper;


import com.yunfeng.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {

    List<CategoryVO> getSubCatList(Integer rootCatId);
}