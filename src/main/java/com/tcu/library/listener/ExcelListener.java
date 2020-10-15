package com.tcu.library.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tcu.library.entity.Books;
import com.tcu.library.service.BooksService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author: yjn
 * @Date: 2020/10/13 0:04
 */
@AllArgsConstructor
@NoArgsConstructor
public class ExcelListener extends AnalysisEventListener<Books> {

    private BooksService booksService;

    @Override
    public void invoke(Books books, AnalysisContext analysisContext) {

        Books bookIsExist = this.bookIsExist(booksService, books.getId());
        if (bookIsExist==null){
            booksService.save(books);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    /**
     * 判断该编号的图书是否存在
     * @param booksService 封装Service和数据库交互
     * @param id 图书编号
     * @return 图书
     */
    private Books bookIsExist(BooksService booksService, String id){
       return booksService.getById(id);
    }
}
