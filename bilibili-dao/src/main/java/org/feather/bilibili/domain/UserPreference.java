package org.feather.bilibili.domain;

import java.util.Date;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.domain
 * @className: UserPreference
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/19 16:39
 * @version: 1.0
 */
public class UserPreference {
    private Long id;

    private Long userId;

    private Long videoId;

    private Float value;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
