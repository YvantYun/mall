package com.yunfeng.controller;


import com.yunfeng.service.ItemESService;
import com.yunfeng.utils.IMOOCJSONResult;
import com.yunfeng.utils.PagedGridResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemESService itemESService;

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/es/search")
    public IMOOCJSONResult search(String keywords,
                                  String sort,
                                  Integer page,
                                  Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }
        // es page 从0凯瑟
        page --;

        PagedGridResult grid = itemESService.searchItems(keywords,
                sort,
                page,
                pageSize);

        return IMOOCJSONResult.ok(grid);
    }
}