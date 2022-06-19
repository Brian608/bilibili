package org.feather.bilibili.repository;

import org.feather.bilibili.domain.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.repository
 * @className: UserInfoRepository
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/6/18 22:17
 * @version: 1.0
 */
public interface UserInfoRepository extends ElasticsearchRepository<UserInfo,Long> {

}
