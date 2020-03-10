package com.yunfeng.config;

import com.yunfeng.service.OrderService;
import com.yunfeng.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 定时器任务
 * </p>
 *
 * @author yunfeng
 * @since 2019-11-27
 */

//@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    //@Scheduled(cron = "0/3 * * * * ?")
//    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        //orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为："
                + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }

}
