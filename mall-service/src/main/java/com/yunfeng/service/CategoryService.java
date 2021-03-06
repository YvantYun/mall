package com.yunfeng.service;

import com.yunfeng.pojo.Category;
import com.yunfeng.pojo.vo.CategoryVO;
import com.yunfeng.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-19
 */

public interface CategoryService {

    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类
     * @param rootCateId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCateId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
