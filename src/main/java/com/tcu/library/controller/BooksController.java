package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.BookQuery;
import com.tcu.library.entity.Books;
import com.tcu.library.mapper.BooksMapper;
import com.tcu.library.service.BooksService;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
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
     * @return
     */
    @GetMapping("/getBookList")
    public ResultEntity getBookList() {
        List<Books> booksList = booksService.list();
        return ResultEntity.ok().data("bookList", booksList);
    }

    /**
     * 根据编号删除图书
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/by/{id}")
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
     * @param bookids
     * @return
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
     * 添加图书
     *
     * @param book
     * @return
     */
    @PostMapping("/add/book")
    public ResultEntity addBook(@RequestBody Books book) {
        boolean save = booksService.save(book);
        if (save) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }

    /**
     * 更新图书
     * @param book
     * @return
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
     * 根据书名或作者查询
     * @param bookQuery
     * @return
     */
    @PostMapping("/condition/query")
    public ResultEntity conditionQuery(@RequestBody(required = false)BookQuery bookQuery){
        QueryWrapper<Books> wrapper=new QueryWrapper<>();
        if (StringUtils.isEmpty(bookQuery.getSearchAuthor())&&StringUtils.isEmpty(bookQuery.getSearchTitle())){
          return  this.getBookList();
        }
        wrapper.like("title", bookQuery.getSearchTitle()).or().like("author", bookQuery.getSearchAuthor());
        List<Books> list = booksService.list(wrapper);
        return ResultEntity.ok().data("row",list);
    }
}

