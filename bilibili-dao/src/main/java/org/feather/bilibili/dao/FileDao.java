package org.feather.bilibili.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.bilibili.domain.File;


@Mapper
public interface FileDao {

    Integer addFile(File file);

    File getFileByMD5(String md5);
}

