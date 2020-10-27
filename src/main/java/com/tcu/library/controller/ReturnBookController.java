package com.tcu.library.controller;


import com.tcu.library.entity.ReturnBook;
import com.tcu.library.service.ReturnBookService;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class ReturnBookController {

    @Autowired
    private ReturnBookService returnBookService;

    /**
     * 查询所有的还书请求单
     * @return 还书请求单
     */
    @GetMapping("/getReturnBookList")
    public ResultEntity getReturnBookList(){
        List<ReturnBook> returnBookList = returnBookService.list();
        return ResultEntity.ok().data("list",returnBookList);
    }
    

}

