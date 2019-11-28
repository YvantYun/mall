package com.yunfeng.service.impl;

import com.yunfeng.mapper.CategoryMapper;
import com.yunfeng.mapper.CategoryMapperCustom;
import com.yunfeng.pojo.Category;
import com.yunfeng.pojo.vo.CategoryVO;
import com.yunfeng.pojo.vo.NewItemsVO;
import com.yunfeng.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-19
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = Example.builder(Category.class)
                .where(WeekendSqls.<Category>custom()
                        .andEqualTo(Category::getType, 1))
                .build();
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCateId) {
        List<CategoryVO> result = categoryMapperCustom.getSubCatList(rootCateId);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {

        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);

        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
