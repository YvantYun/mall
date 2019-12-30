package com.yunfeng.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2019-12-04
 */

@RestController
@RequestMapping("/test")
public class TestController {



    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            response.setHeader("Content-disposition", "attachment;filename=123.xlsx");
            List data = new ArrayList();
            data.add("123");
            data.add("123");
            data.add("123");
            EasyExcel.write(outputStream).sheet("模板").doWrite(dataList());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(outputStream);
        }
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<List<Object>>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<Object>();
            data.add("字符串" + i);
            data.add(0.56);
            list.add(data);
        }
        return list;
    }
}
