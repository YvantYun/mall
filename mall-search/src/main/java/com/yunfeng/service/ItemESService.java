package com.yunfeng.service;

import com.yunfeng.utils.PagedGridResult;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2020-03-09
 */

public interface ItemESService {

    public PagedGridResult searchItems(String keywords,
                                       String sort,
                                       Integer page,
                                       Integer pageSize);
}
