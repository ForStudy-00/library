package com.tcu.library.controller;

import com.tcu.library.service.OssService;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yjn
 * @Date: 2020/10/12 17:03
 */
@RequestMapping("/library/books")
@RestController
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    /**
     *
     * @param file 要上传的图片
     * @return
     */
    @PostMapping("/upload/image")
    public String uploadBookImage(MultipartFile file){
       return ossService.uploadBookImage(file);
    }

    @GetMapping("/download/demo")
    public ResultEntity downloadExcelDemo(){
        return ResultEntity.ok().data("url","https://tcu-libary.oss-cn-beijing.aliyuncs.com/demo.xlsx");
    }
}
