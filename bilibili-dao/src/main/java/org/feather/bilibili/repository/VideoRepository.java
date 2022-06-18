package org.feather.bilibili.repository;

import org.feather.bilibili.domain.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.repository
 * @className: VideoRepository
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/18 22:17
 * @version: 1.0
 */
public interface VideoRepository  extends ElasticsearchRepository<Video,Long> {

    /**
     * 根据title模糊查询
     * @param keyWords
     * @return
     */
    Video findByTitleLike(String keyWords);

}
