package org.feather.bilibili.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.feather.bilibili.domain.UserInfo;
import org.feather.bilibili.domain.Video;
import org.feather.bilibili.repository.UserInfoRepository;
import org.feather.bilibili.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.service
 * @className: ElasticSearchService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/18 22:15
 * @version: 1.0
 */
@Service
public class ElasticSearchService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public  void  addVideo(Video video){
        videoRepository.save(video);
    }
    public  Video getVideos(String keyWords){
        return videoRepository.findByTitleLike(keyWords);
    }

    public List<Map<String, Object>> getContents(String keyWords,Integer pageNo,Integer pageSize) throws IOException {
       String []  indexs={"videos","user-infos"};
        SearchRequest searchRequest=new SearchRequest(indexs);
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.from(pageNo-1);
        searchSourceBuilder.size(pageSize);
        MultiMatchQueryBuilder multiMatchQueryBuilder=new MultiMatchQueryBuilder(keyWords,"title","nick","description");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
         //高亮显示
        String[] array={"title","nick","description"};
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        for (String key:array){
            highlightBuilder.fields().add(new HighlightBuilder.Field(key));
        }
        //如果要多字段高亮，那么需要设置为flase
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span> style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> result=new ArrayList<>();
         for (SearchHit  hit: searchResponse.getHits()){
             //处理高亮字段
             Map<String, HighlightField> highlightFields = hit.getHighlightFields();
             Map<String, Object> sourceAsMap = hit.getSourceAsMap();
             for (String key:array){
                 HighlightField field = highlightFields.get(key);
                 if (field!=null){
                     Text[] fragments = field.fragments();
                     String str= Arrays.toString(fragments);
                     str=str.substring(1,str.length()-1);
                     sourceAsMap.put(key,str);
                 }
             }
             result.add(sourceAsMap);
         }
         return  result;
    }

    public  void  addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }
}
