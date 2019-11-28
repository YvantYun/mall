package com.yunfeng.mapper;

import com.yunfeng.my.mapper.MyMapper;
import com.yunfeng.pojo.ItemsComments;
import com.yunfeng.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    void saveComments(Map<String, Object> map);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}