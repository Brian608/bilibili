package org.feather.bilibili.service.utils;

import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mysql.cj.util.StringUtils;
import org.feather.bilibili.domain.exception.ConditionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

    @Autowired
    private AppendFileStorageClient appendFileStorageClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static  final String DEFAULT_GROUP="group1";

    private static  final String PATH_KEY="path-key:";

    private static  final String UPLOADED_SIZE_KEY="uploaded-size-key:";

    private static  final String UPLOADED_NO_KEY="uploaded-no-key:";


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

    /**
     * 断点续传
     * @param file
     * @return
     */
    public String uploadAppenderFile(MultipartFile file) throws IOException {
        String type = this.getType(file);
        StorePath storePath = appendFileStorageClient.uploadAppenderFile(DEFAULT_GROUP, file.getInputStream(), file.getSize(), type);
        return storePath.getPath();
    }


    public  void modifyAppenderFile(MultipartFile file,String filePath,long offset) throws IOException {
        appendFileStorageClient.modifyFile(DEFAULT_GROUP,filePath,file.getInputStream(),file.getSize(),offset);
    }

    public  String  uploadFileBySlices(MultipartFile file,String fileMd5,Integer sliceNo,Integer totalSliceNo) throws IOException {
        if (file==null || sliceNo==null || totalSliceNo==null){
            throw  new ConditionException("参数异常");
        }
        String pathKey=PATH_KEY+fileMd5;
        String uploadedSizeKey=UPLOADED_SIZE_KEY+fileMd5;
        String uploadedNoKey=UPLOADED_NO_KEY+fileMd5;
        String uploadedSizeStr = redisTemplate.opsForValue().get(uploadedSizeKey);
        long uploadedSize=0L;
        if (!StringUtils.isNullOrEmpty(uploadedSizeStr)){
            uploadedSize=Long.parseLong(uploadedSizeStr);
        }
        String type = this.getType(file);
        //第一个分片
        if (sliceNo==1){
            String path = this.uploadAppenderFile(file);
            if (StringUtils.isNullOrEmpty(path)){
                throw  new ConditionException("上传失败");
            }
            redisTemplate.opsForValue().set(pathKey,path);
            redisTemplate.opsForValue().set(uploadedNoKey,"1");
        }else {
            String filePath = redisTemplate.opsForValue().get(pathKey);
            if (StringUtils.isNullOrEmpty(filePath)){
                throw  new ConnectException("上传失败");
            }
            this.modifyAppenderFile(file,filePath,uploadedSize);
            redisTemplate.opsForValue().increment(uploadedNoKey);
        }
        //修改历史上传文件分片大小
        uploadedSize+=file.getSize();
        redisTemplate.opsForValue().set(uploadedSizeKey,String.valueOf(uploadedSize));
        //如果所有分片全部上传完毕，则清空redis里面相关的key和value
        String uploadNoStr= redisTemplate.opsForValue().get(uploadedNoKey);
        Integer uploadNo = Integer.valueOf(uploadNoStr);
        String resultPath="";
        if (uploadNo.equals(totalSliceNo)){
            resultPath=redisTemplate.opsForValue().get(pathKey);
            List<String> keyList= Arrays.asList(uploadedNoKey,pathKey,uploadedSizeKey);
            redisTemplate.delete(keyList);
        }
       return resultPath;
    }
    //删除
    public  void  deleteFile(String filePath){
        fastFileStorageClient.deleteFile(filePath);
    }
}
