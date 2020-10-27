package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.Books;
import com.tcu.library.entity.Want;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.WantService;
import com.tcu.library.uitls.ResultCode;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yjn
 * @since 2020-10-05
 */
@CrossOrigin
@RestController
@RequestMapping("/library/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @Autowired
    private WantService wantService;

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
     *
     * @param bookids 书籍编号数组
     * @return 是否删除
     */
    @GetMapping("/delete/batch/books")
    public ResultEntity deleteBatchBooks(String[] bookids) {

        try {
            List<String> strings = Arrays.asList(bookids);
            boolean isRemove = booksService.removeByIds(strings);
            if (isRemove) {
                return ResultEntity.ok();
            } else {
                return ResultEntity.error();
            }
        } catch (NullPointerException e) {
            return ResultEntity.error().message("参数为空");
        }

    }

    /**
     * 根据借阅数降序排列
     *
     * @return 降序排列后的图书
     */
    @GetMapping("/getBooks/desc")
    public ResultEntity getBooksDesc() {
        QueryWrapper<Books> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("number");
        List<Books> books = booksService.list(queryWrapper);
        return ResultEntity.ok().data("books", books);
    }

    /**
     * 修改图书状态
     *
     * @param id     图书编号
     * @param status 要修改的状态
     * @return 修改是否成功
     */
    @GetMapping("/update/status/{id}/{status}")
    public ResultEntity updateStatus(@PathVariable String id, @PathVariable int status) {
        Books updateBook = booksService.getById(id);
        updateBook.setStatus(status);
        boolean isUpdate = booksService.updateById(updateBook);
        if (isUpdate) {
            return ResultEntity.ok();
        } else {
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
        if (isExist == null) {
            boolean save = booksService.save(book);
            if (save) {
                return ResultEntity.ok();
            } else {
                return ResultEntity.error();
            }
        }
        return ResultEntity.error().code(ResultCode.ID_EXIST).message("id重复");
    }

    /**
     * 更新图书
     *
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
     *
     * @param id    书籍编号
     * @param title 书名
     * @return 查询的图书
     */
    @GetMapping("/condition/query/{id}/{title}")
    public ResultEntity conditionQuery(@PathVariable(required = false) String id, @PathVariable(required = false) String title, @PathVariable String type) {
        QueryWrapper<Books> wrapper = new QueryWrapper<>();
        //id和title都为空则查询所有
        if (id == null && title == null) {
            return this.getBookList();
        }
        //id为空则根据title查询
        if (id == null) {
            wrapper.eq("title", title);
            Books book = booksService.getOne(wrapper);
            return ResultEntity.ok().data("book", book);
        }
        //title为空则根据id查询
        if (title == null) {
            wrapper.eq("id", id);
            Books book = booksService.getOne(wrapper);
            return ResultEntity.ok().data("book", book);
        }
        return ResultEntity.error();
    }

    /**
     * 查询想看的书籍
     *
     * @param borrowNum 借阅号
     * @return 返回想看的书籍
     */
    @GetMapping("/get/like/book/by/{borrowNum}")
    public ResultEntity getLikeBookByBorrowNum(@PathVariable String borrowNum) {
        QueryWrapper<Want> wantQueryWrapper = new QueryWrapper<>();
        wantQueryWrapper.eq("borrow_num", borrowNum);
        List<Want> wantList = wantService.list(wantQueryWrapper);
        List<Books> bookList = new ArrayList<>();
        wantList.forEach(item -> {
            Books book = booksService.getById(item.getBookId());
            bookList.add(book);
        });
        return ResultEntity.ok().data("books", bookList);
    }

    /**
     * 根据类型查询图书
     *
     * @param type 类型
     * @return 图书列表
     */
    @GetMapping("/getBook/by/{type}")
    public ResultEntity getBookByType(@PathVariable String type) {
        QueryWrapper<Books> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<Books> books = booksService.list(queryWrapper);
        return ResultEntity.ok().data("books", books);
    }

    /**
     * 根据excel添加图书
     *
     * @param file excel表
     * @return 是否添加成功
     */
    @PostMapping("/addBooksByExcel")
    public ResultEntity addBooksByExcel(MultipartFile file) {
        booksService.addBooksByExcel(file, booksService);
        return ResultEntity.ok();
    }
}

