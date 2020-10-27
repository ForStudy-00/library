package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.Books;
import com.tcu.library.entity.Want;
import com.tcu.library.service.BooksService;
import com.tcu.library.service.WantService;
import com.tcu.library.uitls.ResultEntity;
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
    @GetMapping("/getWant/by/{borrowId}")
    public ResultEntity getWantByBorrowId(@PathVariable String borrowId){
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
    @GetMapping("/deleteWant/by/{borrowId}/{bookId}")
    public ResultEntity deleteWant(@PathVariable String borrowId,@PathVariable String bookId){
        QueryWrapper<Want> wantQueryWrapper=new QueryWrapper<>();
        wantQueryWrapper.eq("borrow_num", borrowId);
        wantQueryWrapper.eq("bookId", bookId);
        boolean remove = wantService.remove(wantQueryWrapper);
        if (remove){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }
}

