package com.yunfeng.service.impl;

import com.yunfeng.mapper.CarouselMapper;
import com.yunfeng.pojo.Carousel;
import com.yunfeng.service.CarouselService;
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
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = Example.builder(Carousel.class)
                .where(WeekendSqls.<Carousel>custom()
                        .andEqualTo(Carousel::getIsShow, isShow))
                .orderByDesc("sort")
                .build();
        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
