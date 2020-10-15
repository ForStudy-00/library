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

    @PostMapping("/upload/image")
    public ResultEntity uploadBookImage(MultipartFile file){
        String url = ossService.uploadBookImage(file);
        return ResultEntity.ok().data("url",url);
    }

    @GetMapping("/download/demo")
    public ResultEntity downloadExcelDemo(){
        return ResultEntity.ok().data("url","https://tcu-libary.oss-cn-beijing.aliyuncs.com/demo.xlsx");
    }
}
