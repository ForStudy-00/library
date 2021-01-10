package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.Books;
import com.tcu.library.entity.Want;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.WantService;
import com.tcu.library.uitls.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yjn
 * @since 2020-10-15
 */
@RestController
@CrossOrigin
@RequestMapping("/library/want")
@Api(description = "想看有关的api")
public class WantController {

    @Autowired
    private WantService wantService;

    @Autowired
    private BooksService booksService;

    /**
     * 添加至想看
     * @param want 实体
     * @return 是否成功
     */
    @ApiOperation("添加至想看")
    @PostMapping("/add/to/want")
    public ResultEntity addToWant(@RequestBody Want want) {
        boolean save = wantService.save(want);
        if (save) {
            return ResultEntity.ok();
        } else {
            return ResultEntity.error().message("添加失败");
        }
    }

    /**
     * 根据借阅号查询想看的书籍
     * @param borrowId 借阅号
     * @return 想看的书籍
     */
    @ApiOperation("根据借阅号查询该用户想看的书籍")
    @GetMapping("/getWant/by/{borrowId}")
    public ResultEntity getWantByBorrowId(@ApiParam("用户的借阅号") @PathVariable String borrowId){
        QueryWrapper<Want> wantQueryWrapper=new QueryWrapper<>();
        wantQueryWrapper.eq("borrow_num", borrowId);
        List<Want> list = wantService.list(wantQueryWrapper);
        List<String> ids=new ArrayList<>();
        list.forEach(item->{
           ids.add(item.getBookId()) ;
        });

        List<Books> books = booksService.getBaseMapper().selectBatchIds(ids);
        return ResultEntity.ok().data("books",books);
    }

    /**
     * 根据借阅号和图书编号删除想看表的记录
     * @param borrowId 借阅号
     * @param bookId 图书编号
     * @return 是否成功
     */
    @ApiOperation("根据借阅号和图书编号删除想看的书")
    @GetMapping("/deleteWant/by/{borrowId}/{bookId}")
    public ResultEntity deleteWant(@ApiParam("用户的借阅号")@PathVariable String borrowId,@ApiParam("图书编号")@PathVariable String bookId){
        QueryWrapper<Want> wantQueryWrapper=new QueryWrapper<>();
        wantQueryWrapper.eq("borrow_num", borrowId).eq("book_id", bookId);
//        wantQueryWrapper.eq("bookId", bookId);
        boolean remove = wantService.remove(wantQueryWrapper);
        if (remove){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }
}

