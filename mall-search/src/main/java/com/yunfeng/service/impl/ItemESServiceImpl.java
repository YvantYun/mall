package com.yunfeng.service.impl;

import com.yunfeng.pojo.Items;
import com.yunfeng.pojo.Stu;
import com.yunfeng.service.ItemESService;
import com.yunfeng.utils.PagedGridResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2020-03-09
 */

@Service
public class ItemESServiceImpl implements ItemESService {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public PagedGridResult searchItems(String keywords,
                                       String sort,
                                       Integer page,
                                       Integer pageSize){
        // 自定义高亮
        String preTag = "<font color='red'>";
        String postTag = "</font>";
        // 排序
        SortBuilder sortBuilder = null;
        if(sort.equals("c")) {
            sortBuilder = new FieldSortBuilder("sellCounts")
                    .order(SortOrder.DESC);

        }else if(sort.equals("p")) {
            sortBuilder = new FieldSortBuilder("price")
                    .order(SortOrder.DESC);
        }else {
            sortBuilder = new FieldSortBuilder("itemName.keyword")
                    .order(SortOrder.DESC);
        }

        Pageable pageable = PageRequest.of(page, pageSize);

        String itemNameField = "itemName";

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(itemNameField, keywords))
                .withHighlightFields(new HighlightBuilder.Field(itemNameField)
                        //.preTags(preTag)
                        //.postTags(postTag)
                )
                .withSort(sortBuilder)
                .withPageable(pageable)
                .build();

        AggregatedPage<Items> pageItems = esTemplate.queryForPage(searchQuery, Items.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                List<Items> itemHighLightList = new ArrayList<>();

                for(SearchHit hit : hits) {
                    HighlightField highlightField = hit.getHighlightFields().get(itemNameField);
                    String itemName = highlightField.getFragments()[0].toString();

                    String itemId = (String)hit.getSourceAsMap().get("itemId");
                    String imgUrl = (String)hit.getSourceAsMap().get("imgUrl");
                    Integer price = (Integer)hit.getSourceAsMap().get("price");
                    Integer sellCounts = (Integer)hit.getSourceAsMap().get("sellCounts");

                    Items item = new Items();
                    item.setItemId(itemId);
                    item.setItemName(itemName);
                    item.setImgUrl(imgUrl);
                    item.setSellCounts(sellCounts);
                    item.setPrice(price);
                    itemHighLightList.add(item);
                }

                return new AggregatedPageImpl<>((List<T>) itemHighLightList,
                                                pageable,
                                                searchResponse.getHits().totalHits);
            }
        });
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setRows(pageItems.getContent());
        gridResult.setPage(page + 1);
        gridResult.setTotal(pageItems.getTotalPages());
        gridResult.setRecords(pageItems.getTotalElements());
        return gridResult;
    }
}
