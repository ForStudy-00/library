package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.Books;
import com.tcu.library.entity.UserBook;
import com.tcu.library.entity.Want;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.UserBookService;
import com.tcu.library.service.WantService;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@RestController
@RequestMapping("/library/userBook")
public class UserBookController {
    @Autowired
    private UserBookService userBookService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private WantService wantService;


    QueryWrapper<UserBook> userBookQueryWrapper=new QueryWrapper<>();
    /**
     *
     * @return 所有借阅表信息
     */
    @GetMapping("/getBorrowList")
    public ResultEntity getBorrowList(){
        List<UserBook> userBookList = userBookService.list();
        return ResultEntity.ok().data("list",userBookList);
    }

    /**
     * 添加借阅单
     * @param userBook 借阅单实体
     * @return 是否添加成功
     */
    @PostMapping("/add/borrowingList")
    public ResultEntity addBorrowingList(@RequestBody UserBook userBook){
        boolean save = userBookService.save(userBook);
        if (save){
            //借阅之后让图书的借阅量加1
            Books book = booksService.getById(userBook.getBookId());
            int number = Integer.parseInt(book.getNumber());
            number=number+1;
            book.setNumber(number+"");
            booksService.updateById(book);
            return ResultEntity.ok();
        }else {
            return ResultEntity.error().message("添加失败");
        }
    }

    /**
     * 修改借阅单状态
     * @param id 借阅单编号
     * @param status 要修改的状态
     * @return 是否成功
     */
    @GetMapping("/update/status/{id}/{status}")
    public ResultEntity updateStatus(@PathVariable String id,@PathVariable String status){
        UserBook userBook = userBookService.getById(id);
        if (userBook==null){
            return ResultEntity.error().message("订单号错误");
        }else {
            userBook.setStatus(status);
            userBookService.updateById(userBook);
            return ResultEntity.ok();
        }
    }

    /**
     * 根据借阅号查询借阅历史
     * @param id 借阅号
     * @return 借阅历史
     */
    @GetMapping("/getOneList/by/{id}")
    public ResultEntity getOneList(@PathVariable String id){
        userBookQueryWrapper.eq("borrow_num", id);
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return ResultEntity.ok().data("list",list);
    }

    /**
     * 查询该用户已借的书是否超过三本
     * @param borrowNum 用户的借阅号
     * @return 是否超过三本书
     */
    @GetMapping("/isMoreThanThree/{borrowNum}")
    public boolean isMoreThanThree(@PathVariable String borrowNum){
        userBookQueryWrapper.eq("borrow_num", borrowNum);
        userBookQueryWrapper.eq("status", "1").or().eq("status", "3");
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return list.size() <= 3;
    }


    /**
     * 添加至想看
     * @param want 图书的编号和用户的借阅号
     * @return 是否添加成功
     */
    @PostMapping("/add/to/like")
    public ResultEntity addToLike(@RequestBody Want want){
        boolean save = wantService.save(want);
        if (save){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error().message("保存失败");
        }
    }
}

