package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.ReturnBook;
import com.tcu.library.entity.UserBook;
import com.tcu.library.service.ReturnBookService;
import com.tcu.library.service.UserBookService;
import com.tcu.library.uitls.ResultCode;
import com.tcu.library.uitls.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yjn
 * @since 2020-10-19
 */
@RestController
@CrossOrigin
@RequestMapping("/library/returnBook")
@Api(description = "还书申请单的api")
public class ReturnBookController {

    @Autowired
    private ReturnBookService returnBookService;
    @Autowired
    private UserBookService userBookService;

    /**
     * 查询所有的还书请求单
     * @return 还书请求单
     */
    @ApiOperation("查询所有的还书请求单")
    @GetMapping("/getReturnBookList")
    public ResultEntity getReturnBookList(){
        List<ReturnBook> returnBookList = returnBookService.list();
        return ResultEntity.ok().data("list",returnBookList);
    }

    @ApiOperation("添加还书申请单")
    @PostMapping("/addReturnBook")
    @Transactional
    public ResultEntity addReturnBook(@RequestBody ReturnBook returnBook){
        //还书的时候要根据还书的日期修改相应的借阅单的归还时间
        //先查出来两个表共同的字段 bookid和borrownum 并且是在借阅中的记录
        String bookId = returnBook.getBookId();
        BigInteger borrowNum = returnBook.getBorrowNum();
        //要修改为的时间
        Date returnTime = returnBook.getReturnTime();

        boolean save = returnBookService.save(returnBook);
        QueryWrapper<UserBook> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId).eq("borrow_num", borrowNum).orderByDesc("create_time");
        //查询出借阅表的对应记录,修改时间然后更新
       List<UserBook> userBooks = userBookService.list(queryWrapper);
        UserBook userBook = userBooks.get(0);
        userBook.setReturnTime(returnTime);
        boolean update = userBookService.updateById(userBook);
        if (save &&update){
            return ResultEntity.ok();
        }
        return ResultEntity.error().code(ResultCode.ERROR).message("添加失败");
    }

    @ApiOperation("删除还书申请单")
    @PostMapping("/delReturnBook/{id}")
    public ResultEntity delReturnBook(@PathVariable("id") String id){

        boolean b = returnBookService.removeById(id);
        if (b){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error().code(ResultCode.ERROR).message("删除失败");
        }
    }

}

