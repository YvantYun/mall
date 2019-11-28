package com.yunfeng.service.center;

import com.yunfeng.pojo.OrderItems;
import com.yunfeng.pojo.Users;
import com.yunfeng.pojo.bo.center.CenterUserBO;
import com.yunfeng.pojo.bo.center.OrderItemsCommentBO;
import com.yunfeng.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
     void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
     PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
