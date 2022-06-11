package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.Video;
import org.feather.bilibili.domain.VideoTag;

import java.util.List;
import java.util.Map;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.dao
 * @className: VideoDao
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/7 22:19
 * @version: 1.0
 */
@Mapper
public interface VideoDao {

    Integer addVideos(Video video);

    Integer batchAddVideoTags(List<VideoTag> videoTagList);

    Integer pageListVideosCount(Map<String, Object> paramMap);

    List<Video> pageListVideos(Map<String, Object> paramMap);
}
