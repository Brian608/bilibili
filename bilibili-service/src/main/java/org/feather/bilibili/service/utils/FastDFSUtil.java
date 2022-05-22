package org.feather.bilibili.service.utils;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mysql.cj.util.StringUtils;
import org.feather.bilibili.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.service.utils
 * @className: FastDFSUtil
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/5/22 16:29
 * @version: 1.0
 */
public class FastDFSUtil {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    public  String getType(MultipartFile file){
        if (file==null){
            throw  new ConditionException("非法文件");
        }
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isNullOrEmpty(originalFilename)){
            throw new ConditionException("文件名为空");
        }
        int index = originalFilename.lastIndexOf(".");
        return  originalFilename.substring(index + 1);
    }

    //上传
    public  String uploadCommonFile(MultipartFile file) throws IOException {
        Set<MetaData> metaDataSet=new HashSet<>();
        String type = this.getType(file);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), type, metaDataSet);
        return  storePath.getPath();
    }

    //删除
    public  void  deleteFile(String filePath){
        fastFileStorageClient.deleteFile(filePath);
    }
}
