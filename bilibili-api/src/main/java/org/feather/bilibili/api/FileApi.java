package org.feather.bilibili.api;

import org.feather.bilibili.domain.JsonResponse;
import org.feather.bilibili.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @projectName: bilibili
 * @package: org.feather.bilibili.api
 * @className: FileApi
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/5/29 17:50
 * @version: 1.0
 */
@RestController
public class FileApi {
        @Autowired
        private FileService fileService;

        @PostMapping("/md5files")
        public JsonResponse<String> getFileMD5(MultipartFile file) throws Exception {
                String fileMD5 = fileService.getFileMD5(file);
                return new JsonResponse<>(fileMD5);
        }

        @PutMapping("/file-slices")
        public JsonResponse<String> uploadFileBySlices(MultipartFile slice,
                                                       String fileMd5,
                                                       Integer sliceNo,
                                                       Integer totalSliceNo) throws Exception {
                String filePath = fileService.uploadFileBySlices(slice,fileMd5,sliceNo,totalSliceNo);
                return  new JsonResponse<>(filePath);

        }
}
