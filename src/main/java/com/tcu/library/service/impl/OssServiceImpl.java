package com.tcu.library.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tcu.library.entity.OssProperties;
import com.tcu.library.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: yjn
 * @Date: 2020/10/12 17:04
 */
@Service
public class OssServiceImpl implements OssService {




    /**
     * 上传图片
     * @param file 图书封面图片
     * @return 图片地址
     */
    @Override
    public String uploadBookImage(MultipartFile file) {
        String endpoint = OssProperties.END_POINT;
        String accessKeyId = OssProperties.KEY_ID;
        String accessKeySecret = OssProperties.KEY_SECRET;
        String bucketName=OssProperties.BUCKET_NAME;
        //获取文件的名称
        String filename = file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename=bucketName+"/"+uuid+filename;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, 																	accessKeySecret);

        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            //第二个参数表示上传到oss的文件路径和文件名 eg:aa/bb/1.jpg
            ossClient.putObject(bucketName,filename , inputStream);

            //获取上传文件的地址
            String url="https://"+bucketName+"."+endpoint+"/"+filename;

            // 关闭OSSClient。
            ossClient.shutdown();
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

}
