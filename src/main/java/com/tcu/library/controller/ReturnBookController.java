package com.tcu.library.controller;


import com.tcu.library.entity.ReturnBook;
import com.tcu.library.service.ReturnBookService;
import com.tcu.library.uitls.ResultCode;
import com.tcu.library.uitls.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultEntity addReturnBook(@RequestBody ReturnBook returnBook){
        boolean save = returnBookService.save(returnBook);
        if (save){
            return ResultEntity.ok();
        }
        return ResultEntity.error().code(ResultCode.ERROR).message("添加失败");
    }

}

