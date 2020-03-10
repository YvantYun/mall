package com.yunfeng.pojo;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;


@Document(indexName = "mall-items-ik", type = "doc", createIndex = false)
@Data
public class Items {
    /**
     * 商品主键id
     */
    @Id
    @Field(store = true, type = FieldType.Text, index = false)
    private String itemId;

    /**
     * 商品名称 商品名称
     */
    @Field(store = true, type = FieldType.Text, index = true)
    private String itemName;

    @Field(store = true, type = FieldType.Text, index = false)
    private String imgUrl;

    @Field(store = true, type = FieldType.Integer)
    private Integer price;

    @Field(store = true, type = FieldType.Integer)
    private Integer sellCounts;



}