package com.test;

import com.yunfeng.ESApplication;
import com.yunfeng.pojo.Stu;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author yunfeng
 * @since 2020-03-08
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    private final String docId = "aqwYvXABeEfy5ABZXuep";

    /**
     * 不建议使用ElasticsearchTemplate进行索引管理（创建索引，更新映射，删除索引）
     * 在es中尽量使用ElasticsearchTemplate对文档进行CRUD操作
     */
    @Test
    public void createIndexStu() {
        Stu stu = new Stu();
        stu.setStuId(1001L);
        stu.setName("iron man");
        stu.setAge(24);
        stu.setMoney(18.8f);
        stu.setSign("I am a iron man");
        stu.setDescription("我是一个钢铁侠");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(stu).build();
        esTemplate.index(indexQuery);

    }

    @Test
    public void deleteIndex() {
        esTemplate.deleteIndex(Stu.class);
    }

    //  -------------------------------文档操作---------------------------------

    @Test
    public void updateStuDoc() {

        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("sign", "i am not super man");
        sourceMap.put("money", 88.6f);
        sourceMap.put("age", 32);

        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(sourceMap);

        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Stu.class)
                .withId(docId)
                .withIndexRequest(indexRequest)
                .build();


        esTemplate.update(updateQuery);
    }

    @Test
    public void queryStuDoc() {

        GetQuery query = new GetQuery();
        query.setId(docId);

        Stu stu = esTemplate.queryForObject(query, Stu.class);
        System.out.println(stu);
    }


    @Test
    public void deleteStuDoc() {

        esTemplate.delete(Stu.class, "a6wZvXABeEfy5ABZzedG");
    }

    //  -------------------------------分割线---------------------------------

    @Test
    public void searchStuDoc() {

        Pageable pageable = PageRequest.of(0, 10);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "钢铁侠"))
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> pageStu = esTemplate.queryForPage(searchQuery, Stu.class);
        System.out.println("总页数是：" + pageStu.getTotalPages());
        List<Stu> stuList = pageStu.getContent();
        stuList.forEach(System.out::println);
    }


    @Test
    public void highLightStuDoc() {
        String preTag = "<font color='red'>";
        String postTag = "</font>";

        Pageable pageable = PageRequest.of(0, 2);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("description", "钢铁侠"))
                .withHighlightFields(new HighlightBuilder.Field("description")
                        .preTags(preTag)
                        .postTags(postTag))
                .withPageable(pageable)
                .build();
        AggregatedPage<Stu> pageStu = esTemplate.queryForPage(searchQuery, Stu.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                List<Stu> stuHighLightList = new ArrayList<>();
                for(SearchHit hit : hits) {
                    HighlightField highlightField = hit.getHighlightFields().get("description");
                    String description = highlightField.getFragments()[0].toString();
                    Object stuId = (Object)hit.getSourceAsMap().get("stuId");
                    String name = (String)hit.getSourceAsMap().get("name");
                    Integer age = (Integer)hit.getSourceAsMap().get("age");
                    String sign = (String)hit.getSourceAsMap().get("sign");
                    Object money = (Object)hit.getSourceAsMap().get("money");
                    Stu stu = new Stu();
                    stu.setDescription(description);
                    stu.setStuId(Long.valueOf(stuId.toString()));
                    stu.setName(name);
                    stu.setAge(age);
                    stu.setSign(sign);
                    stu.setMoney(Float.valueOf(money.toString()));
                    stuHighLightList.add(stu);
                }
                if(stuHighLightList.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) stuHighLightList);
                }
                return null;
            }
        });
        System.out.println("总页数是：" + pageStu.getTotalPages());
        List<Stu> stuList = pageStu.getContent();
        stuList.forEach(System.out::println);
    }





}
