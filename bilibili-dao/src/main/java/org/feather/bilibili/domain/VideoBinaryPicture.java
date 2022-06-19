package org.feather.bilibili.domain;

import java.util.Date;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.domain
 * @className: VideoBinaryPicture
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/19 17:00
 * @version: 1.0
 */
public class VideoBinaryPicture {
    private Long id;

    private Long videoId;

    private Integer frameNo;

    private String url;

    private Long videoTimestamp;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Integer getFrameNo() {
        return frameNo;
    }

    public void setFrameNo(Integer frameNo) {
        this.frameNo = frameNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getVideoTimestamp() {
        return videoTimestamp;
    }

    public void setVideoTimestamp(Long videoTimestamp) {
        this.videoTimestamp = videoTimestamp;
    }
}
