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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
@CrossOrigin
@RequestMapping("/library/userBook")
@Api(description = "借阅单的api")
public class UserBookController {
    @Autowired
    private UserBookService userBookService;

    @Autowired
    private BooksService booksService;


    /**
     *
     * @return 所有借阅表信息
     */
    @ApiOperation("查询所有借阅表信息")
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
    @ApiOperation("添加借阅单信息")
    @PostMapping("/add/borrowingList")
    public ResultEntity addBorrowingList(@RequestBody UserBook userBook){
        boolean save = userBookService.save(userBook);
        if (save){
            //借阅之后让图书的借阅量加1
            Books book = booksService.getById(userBook.getBookId());
            int number = book.getNumber();
            number=number+1;
            book.setNumber(number);
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
    @ApiOperation(value = "修改借阅单状态",notes = " 已预约:0 借阅中:1 已归还:2 已逾期:3")
    @GetMapping("/update/status/{id}/{status}")
    public ResultEntity updateStatus(@ApiParam("借阅单的id") @PathVariable String id,@ApiParam("要修改为的状态") @PathVariable int status){
        UserBook userBook = userBookService.getById(id);
        if (userBook==null){
            return ResultEntity.error().message("借阅单号错误");
        }else {
            userBook.setStatus(status);
            userBookService.updateById(userBook);
            return ResultEntity.ok();
        }
    }

    /**
     * 根据借阅号查询借阅历史
     * @param borrowNum 借阅号
     * @return 借阅历史
     */
    @ApiOperation("根据借阅号查询借阅历史")
    @GetMapping("/getOneList/by/{borrowNum}")
    public ResultEntity getOneList(@ApiParam("用户借阅号")@PathVariable String borrowNum){
        QueryWrapper<UserBook> userBookQueryWrapper=new QueryWrapper<>();
        userBookQueryWrapper.eq("borrow_num", borrowNum);
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return ResultEntity.ok().data("list",list);
    }

    /**
     * 根据借阅单id删除记录
     * @param id 借阅单号
     * @return 是否删除
     */
    @ApiOperation("根据借阅单id删除记录")
    @GetMapping("/delete/by/{id}")
    public ResultEntity deleteById(@ApiParam("借阅单id")@PathVariable String id){
        boolean isRemoved = userBookService.removeById(id);
        if (isRemoved){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }

    /**
     * 查询该用户已借的书是否超过三本
     * @param borrowNum 用户的借阅号
     * @return 是否超过三本书
     */
    @ApiOperation(value = "查询该用户已借的书是否超过三本",notes = "true:没有超过三本,false:超过了三本")
    @GetMapping("/isMoreThanThree/{borrowNum}")
    public boolean isMoreThanThree(@ApiParam("用户的借阅号") @PathVariable String borrowNum){
        QueryWrapper<UserBook> userBookQueryWrapper=new QueryWrapper<>();
        userBookQueryWrapper.eq("borrow_num", borrowNum);
        userBookQueryWrapper.eq("status", "1").or().eq("status", "3");
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return list.size() <= 3;
    }

    @ApiOperation(value = "根据借阅号查询待取或已取书的书籍信息")
    @GetMapping("/getDetails/pending/taken/{borrowNum}")
    public ResultEntity getDetailsPendingTaken(@PathVariable String borrowNum){
        ArrayList<PendTakenBook> detailsPendingTaken = userBookService.getDetailsPendingTaken(borrowNum);
        if (detailsPendingTaken.size()!=0){

        return ResultEntity.ok().data("details",detailsPendingTaken);
        }else {
            return ResultEntity.ok().message("无数据").code(20006);
        }

    }


    /**
     * 检查借阅是否逾期
     * 每天凌晨一点执行
     */
    @Scheduled(cron = "0 0 1 * * ?")
    void checkOverdue(){
        List<HashMap<String, Object>> returnTimeList = userBookService.getReturnTime();
        Date today=new Date();
        returnTimeList.forEach((item)->{
            Date returnTime = (Date) item.get("return_time");
            String id = (String) item.get("id");
            //判断还书日期是否超过今天
            if (today.after(returnTime)){
                userBookService.updataStatusToThree(id);
            }
        });
    }

}

