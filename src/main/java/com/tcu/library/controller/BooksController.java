package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.Books;
import com.tcu.library.service.BooksService;
import com.tcu.library.uitls.ResultCode;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author yjn
 * @since 2020-10-05
 */
@CrossOrigin
@RestController
@RequestMapping("/library/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    /**
     * 查询所有图书
     *
     * @return 所有图书
     */
    @GetMapping("/getBookList")
    public ResultEntity getBookList() {
        List<Books> booksList = booksService.list();
        return ResultEntity.ok().data("bookList", booksList);
    }

    /**
     * 根据编号删除图书
     *
     * @param id 书籍编号
     * @return 是否删除
     */
    @GetMapping("/delete/by/{id}")
    public ResultEntity deleteById(@PathVariable String id) {
        boolean isRemoved = booksService.removeById(id);
        if (isRemoved) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }

    /**
     * 根据id批量删除图书
     * @param bookids 书籍编号数组
     * @return 是否删除
     */
    @GetMapping("/delete/batch/books")
    public ResultEntity deleteBatchBooks(String[] bookids){

        List<String> strings = Arrays.asList(bookids);
        boolean isRemove = booksService.removeByIds(strings);
        if (isRemove){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }

    /**
     * 修改图书状态
     * @param id 图书编号
     * @param status 要修改的状态
     * @return 修改是否成功
     */
    @GetMapping("/update/status/{id}/{status}")
    public ResultEntity updateStatus(@PathVariable String id,@PathVariable String status){
        Books updateBook = booksService.getById(id);
        updateBook.setStatus(status);
        boolean isUpdate = booksService.updateById(updateBook);
        if (isUpdate){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }
    /**
     * 添加图书
     *
     * @param book 要添加的图书
     * @return 添加的状态
     */
    @PostMapping("/add/book")
    public ResultEntity addBook(@RequestBody Books book) {
        Books isExist = booksService.getById(book.getId());
        if (isExist==null){
        boolean save = booksService.save(book);
        if (save){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
        }
            return ResultEntity.error().code(ResultCode.ID_EXIST).message("id重复");
    }

    /**
     * 更新图书
     * @param book 要更新的图书
     * @return 更新的状态
     */
    @PostMapping("/update/book")
    public ResultEntity updateBook(@RequestBody Books book) {
        boolean update = booksService.updateById(book);
        if (update) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }


    /**
     * 根据书名或者编号查询
     * @param id 书籍编号
     * @param title 书名
     * @return 查询的图书
     */
    @GetMapping("/condition/query/{id}/{title}")
    public ResultEntity conditionQuery(@PathVariable(required = false) String id,@PathVariable(required = false) String title){
        QueryWrapper<Books> wrapper=new QueryWrapper<>();
        //id和title都为空则查询所有
        if (id==null&&title==null){
          return  this.getBookList();
        }
        //id为空则根据title查询
        if (id==null){
            wrapper.eq("title", title);
            Books book = booksService.getOne(wrapper);
            return ResultEntity.ok().data("book",book);
        }
        //title为空则根据id查询
        if (title==null){
            wrapper.eq("id", id);
            Books book = booksService.getOne(wrapper);
            return ResultEntity.ok().data("book",book);
        }
        return ResultEntity.error();
    }

    @PostMapping("/addBooksByExcel")
    public ResultEntity addBooksByExcel(MultipartFile file){
    booksService.addBooksByExcel(file,booksService);
    return ResultEntity.ok();
    }
}

