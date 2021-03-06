package com.yunfeng.controller;

import com.yunfeng.enums.YesOrNo;
import com.yunfeng.pojo.Carousel;
import com.yunfeng.pojo.Category;
import com.yunfeng.pojo.vo.CategoryVO;
import com.yunfeng.pojo.vo.NewItemsVO;
import com.yunfeng.service.CarouselService;
import com.yunfeng.service.CategoryService;
import com.yunfeng.utils.IMOOCJSONResult;
import com.yunfeng.utils.JsonUtils;
import com.yunfeng.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-14
 */
@Api(value = "首页", tags = {"首页展示相关接口"})
@RestController
@RequestMapping("/index")
@Slf4j
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisOperator redisOperator;


    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {

        List<Carousel> list;

        String carouselStr = redisOperator.get("carousel");
        if(StringUtils.isBlank(carouselStr)) {
           list = carouselService.queryAll(YesOrNo.YES.type);
            // list -> json 存储在redis中
            redisOperator.set("carousel", JsonUtils.objectToJson(list));
        }else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }

        return IMOOCJSONResult.ok(list);
    }

    /**
     * 首页分类展示需求
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）",httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        List<Category> list;
        String catsStr = redisOperator.get("category");
        if(StringUtils.isBlank(catsStr)) {
            list = categoryService.queryAllRootLevelCat();
            redisOperator.set("category", JsonUtils.objectToJson(list));
        }else {
            list = JsonUtils.jsonToList(catsStr, Category.class);
        }

        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "获取商品分类（二、三级分类）", notes = "获取商品分类（二、三级分类）",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if(rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list;
        String subCatStr = redisOperator.get("subCat:" + rootCatId);
        if(StringUtils.isBlank(subCatStr)) {
            list = categoryService.getSubCatList(rootCatId);
            if(list != null && list.size() > 0) {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list));
            }else {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list), 5 * 60);
            }

        }else {
            list = JsonUtils.jsonToList(subCatStr, CategoryVO.class);
        }

        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }

}
