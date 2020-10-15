package com.tcu.library.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yjn
 * @Date: 2020/10/12 17:04
 */
public interface OssService {

    /**
     * 上传图书封面图片
     * @param file 图书封面图片
     * @return 图书封面地址
     */
    String uploadBookImage(MultipartFile file);

}
