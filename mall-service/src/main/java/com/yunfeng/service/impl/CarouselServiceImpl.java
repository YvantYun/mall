package com.yunfeng.service.impl;

import com.yunfeng.mapper.CarouselMapper;
import com.yunfeng.pojo.Carousel;
import com.yunfeng.service.CarouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class CarouseServiceImpl implements CarouseService {

    @Autowired
    private CarouselMapper 
    @Override
    public List<Carousel> queryAll(Integer isShow) {

        return null;
    }
}
