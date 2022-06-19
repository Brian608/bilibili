package org.feather.bilibili.api;

import org.feather.bilibili.domain.JsonResponse;
import org.feather.bilibili.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.api
 * @className: SystemApi
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/19 08:18
 * @version: 1.0
 */
@RestController
public class SystemApi {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping("/contents")
    public JsonResponse<List<Map<String, Object>>> getContents(@RequestParam String keyword,
                                                               @RequestParam Integer pageNo,
                                                               @RequestParam Integer pageSize) throws IOException {
        List<Map<String, Object>> list = elasticSearchService.getContents(keyword,pageNo,pageSize);
        return new JsonResponse<>(list);
    }
}
