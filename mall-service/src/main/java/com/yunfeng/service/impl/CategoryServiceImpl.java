package com.yunfeng.service.impl;

import com.yunfeng.mapper.CategoryMapper;
import com.yunfeng.pojo.Category;
import com.yunfeng.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

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

    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = Example.builder(Category.class)
                .where(WeekendSqls.<Category>custom()
                        .andEqualTo(Category::getType, 1))
                .build();
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }
}
