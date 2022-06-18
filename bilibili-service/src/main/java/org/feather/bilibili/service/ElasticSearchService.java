package org.feather.bilibili.service;

import org.feather.bilibili.domain.Video;
import org.feather.bilibili.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public  void  addVideo(Video video){
        videoRepository.save(video);
    }
    public  Video getVideos(String keyWords){
        return videoRepository.findByTitleLike(keyWords);
    }
}
