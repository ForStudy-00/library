package com.tcu.library.service.impl;

import com.alibaba.excel.EasyExcel;
import com.tcu.library.entity.Books;
import com.tcu.library.listener.ExcelListener;
import com.tcu.library.mapper.BooksMapper;
import com.tcu.library.service.BooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjn
 * @since 2020-10-05
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {

    @Override
    public void addBooksByExcel(MultipartFile file, BooksService booksService) {
        try {
            InputStream fileInputStream = file.getInputStream();
            EasyExcel.read(fileInputStream, Books.class, new ExcelListener(booksService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
