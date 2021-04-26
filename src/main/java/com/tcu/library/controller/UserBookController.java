package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.Books;
import com.tcu.library.entity.PendTakenBook;
import com.tcu.library.entity.UserBook;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.UserBookService;
import com.tcu.library.uitls.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@RestController
@CrossOrigin
@RequestMapping("/library/userBook")
@Api(description = "借阅单的api")
public class UserBookController {
    @Autowired
    private UserBookService userBookService;

    @Autowired
    private BooksService booksService;


    /**
     * @return 所有借阅表信息
     */
    @ApiOperation("查询所有借阅表信息")
    @GetMapping("/getBorrowList")
    public ResultEntity getBorrowList() {
        List<UserBook> userBookList = userBookService.list();
        return ResultEntity.ok().data("list", userBookList);
    }

    /**
     * 添加借阅单
     *
     * @param userBook 借阅单实体
     * @return 是否添加成功
     */
    @Transactional
    @ApiOperation("添加借阅单信息")
    @PostMapping("/add/borrowingList")
    public ResultEntity addBorrowingList(@RequestBody UserBook userBook) {
        boolean save = userBookService.save(userBook);
        if (save) {
            //借阅之后让图书的借阅量加1
            Books book = booksService.getById(userBook.getBookId());
            int number = book.getNumber();
            number = number + 1;
            book.setNumber(number);
            book.setStatus(1);
            booksService.updateById(book);
            return ResultEntity.ok();
        } else {
            return ResultEntity.error().message("添加失败");
        }
    }

    /**
     * 修改借阅单状态
     *
     * @param bookId    书籍编号
     * @param borrowNum 借阅号
     * @param status    要修改的状态
     * @return 是否成功
     */
    @ApiOperation(value = "修改借阅单状态", notes = " 已预约:0 借阅中:1 已归还:2 已逾期:3")
    @GetMapping("/update/status/{bookId}/{borrowNum}/{status}")
    public ResultEntity updateStatus(@ApiParam("书籍编号") @PathVariable String bookId, @ApiParam("借阅号") @PathVariable String borrowNum, @ApiParam("要修改为的状态") @PathVariable int status) {
        QueryWrapper<UserBook> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId).eq("borrow_num", borrowNum).orderByDesc("create_time");
        List<UserBook> list = userBookService.list(queryWrapper);
        UserBook userBook = list.get(0);
        if (userBook == null) {
            return ResultEntity.error().message("借阅单号错误");
        } else {
            userBook.setStatus(status);
            //修改默认还书时间为真正还书的时间
            if (status == 1) {
                userBook.setTakeTime(new Date());
            }
            if (status == 2) {
                userBook.setReturnTime(new Date());
            }
            userBookService.updateById(userBook);
            return ResultEntity.ok();
        }
    }

    /**
     * 根据借阅号查询借阅历史
     *
     * @param borrowNum 借阅号
     * @return 借阅历史
     */
    @ApiOperation("根据借阅号查询借阅历史")
    @GetMapping("/getOneList/by/{borrowNum}")
    public ResultEntity getOneList(@ApiParam("用户借阅号") @PathVariable String borrowNum) {
        QueryWrapper<UserBook> userBookQueryWrapper = new QueryWrapper<>();
        userBookQueryWrapper.eq("borrow_num", borrowNum).orderByDesc("create_time");
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return ResultEntity.ok().data("list", list);
    }

    /**
     * 根据借阅单id删除记录
     *
     * @param id 借阅单号
     * @return 是否删除
     */
    @ApiOperation("根据借阅单id删除记录")
    @GetMapping("/delete/by/{id}")
    public ResultEntity deleteById(@ApiParam("借阅单id") @PathVariable String id) {
        boolean isRemoved = userBookService.removeById(id);
        if (isRemoved) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error();
        }
    }

    /**
     * 查询该用户已借的书是否超过三本
     *
     * @param borrowNum 用户的借阅号
     * @return 是否超过三本书
     */
    @ApiOperation(value = "查询该用户已借的书是否大于等于三本", notes = "true:大于等于三本,false:没有超过三本")
    @GetMapping("/isMoreThanThree/{borrowNum}")
    public boolean isMoreThanThree(@ApiParam("用户的借阅号") @PathVariable String borrowNum) {
        QueryWrapper<UserBook> userBookQueryWrapper = new QueryWrapper<>();
        userBookQueryWrapper.eq("borrow_num", borrowNum);
        userBookQueryWrapper.ne("status", 2);
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return list.size() >= 3;
    }

    @ApiOperation(value = "根据借阅号查询待取或已取书的书籍信息")
    @GetMapping("/getDetails/pending/taken/{borrowNum}")
    public ResultEntity getDetailsPendingTaken(@PathVariable String borrowNum) {
        ArrayList<PendTakenBook> detailsPendingTaken = userBookService.getDetailsPendingTaken(borrowNum);
        if (detailsPendingTaken.size() != 0) {

            return ResultEntity.ok().data("details", detailsPendingTaken);
        } else {
            return ResultEntity.ok().message("无数据").code(20006);
        }

    }

    @ApiOperation(value = "查询该用户借阅中的书本数量")
    @GetMapping("/get/BorrowedBookNum/by/{borrowNum}")
    public ResultEntity borrowedBookNum(@ApiParam("用户的借阅号") @PathVariable String borrowNum) {
        int number = userBookService.getBorrowedBookNum(borrowNum);
        return ResultEntity.ok().data("number", number);
    }

    /**
     * 检查借阅是否逾期
     * 每天23:59:59执行
     */
    @Scheduled(cron = "59 59 23 * * ?")
    void checkOverdue() {
        List<HashMap<String, Object>> returnTimeList = userBookService.getReturnTime();
        Date today = new Date();
        returnTimeList.forEach((item) -> {
            Date returnTime = (Date) item.get("return_time");
            String id = item.get("id").toString();
            //判断还书日期是否超过今天
            if (today.after(returnTime)) {
                userBookService.updataStatusToThree(id);
            }
        });
    }

}

