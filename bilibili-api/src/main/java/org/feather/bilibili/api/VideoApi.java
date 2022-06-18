package org.feather.bilibili.api;

import org.feather.bilibili.api.support.UserSupport;
import org.feather.bilibili.domain.JsonResponse;
import org.feather.bilibili.domain.PageResult;
import org.feather.bilibili.domain.Video;
import org.feather.bilibili.service.ElasticSearchService;
import org.feather.bilibili.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.api
 * @className: VideoApi
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/7 22:39
 * @version: 1.0
 */
@RestController
public class VideoApi {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ElasticSearchService elasticSearchService;

    /**
     * 视频投稿
     */
    @PostMapping("/videos")
    public JsonResponse<String> addVideos(@RequestBody Video video){
        Long userId = userSupport.getCurrentUserId();
        video.setUserId(userId);
        videoService.addVideos(video);
        //在es中添加数据
        elasticSearchService.addVideo(video);
        return JsonResponse.success();
    }
    @GetMapping("/videos")
    public JsonResponse<PageResult<Video>> pageListVideos(Integer size,Integer no,String area){
     PageResult<Video> result=   videoService.pageListVideos(size,no,area);
        return  new JsonResponse<>(result);
    }
    @GetMapping("/video-slices")
    public void  viewVideoOnlineBySlices(HttpServletRequest request, HttpServletResponse response,String url) throws Exception {
        videoService.viewVideoOnlineBySlices(request,response,url);
    }
}
