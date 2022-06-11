package org.feather.bilibili.service;

import org.feather.bilibili.dao.VideoDao;
import org.feather.bilibili.domain.PageResult;
import org.feather.bilibili.domain.Video;
import org.feather.bilibili.domain.VideoTag;
import org.feather.bilibili.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.service
 * @className: VideoService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/7 22:22
 * @version: 1.0
 */
@Service
public class VideoService {

    @Autowired
    private VideoDao videoDao;

    @Transactional(rollbackFor = Exception.class)
    public void addVideos(Video video) {
        Date now = new Date();
        video.setCreateTime(new Date());
        videoDao.addVideos(video);
        Long videoId = video.getId();
        List<VideoTag> tagList = video.getVideoTagList();
        tagList.forEach(item -> {
            item.setCreateTime(now);
            item.setVideoId(videoId);
        });
        videoDao.batchAddVideoTags(tagList);
    }

    public PageResult<Video> pageListVideos(Integer size, Integer no, String area) {
        if (size==null||no==null){
            throw  new ConditionException("查询参数为空！");
        }
        Map<String, Object> paramMap=new HashMap<>();
        paramMap.put("start",(no-1)*size);
        paramMap.put("limit",size);
        paramMap.put("area",area);
        List<Video> list=new ArrayList<>();
        Integer total = videoDao.pageListVideosCount(paramMap);
        if (total>0){
            list=videoDao.pageListVideos(paramMap);
        }
        return null;
    }
}
