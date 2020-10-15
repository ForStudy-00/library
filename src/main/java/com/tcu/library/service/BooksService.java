package com.tcu.library.service;

import com.tcu.library.entity.Books;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjn
 * @since 2020-10-05
 */
public interface BooksService extends IService<Books> {

    /**
     * 通过excel添加图书
     * @param file excel表
     * @param booksService Service和数据库交互
     */
    void addBooksByExcel(MultipartFile file, BooksService booksService);
}
