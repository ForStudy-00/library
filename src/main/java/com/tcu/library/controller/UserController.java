package com.tcu.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tcu.library.entity.User;
import com.tcu.library.service.UserService;
import com.tcu.library.uitls.ResultCode;
import com.tcu.library.uitls.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@CrossOrigin
@RestController
@RequestMapping("/library/user")
@Api(description = "用户的api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     * @param user 用户信息
     * @return 添加的状态
     */
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public ResultEntity addUser(@RequestBody User user){
        User getUser = userService.getById(user.getBorrowNum());
        if (getUser!=null){
            return ResultEntity.error().code(ResultCode.ID_EXIST).message("借阅号已存在");
        }
        boolean save = userService.save(user);
        if (save){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }

    /**
     * 查询所有用户
     * @return 所有用户信息
     */
    @ApiOperation("查询所有用户")
    @GetMapping("/getUserList")
    public ResultEntity getUserList(){
        List<User> userList = userService.list();
        return ResultEntity.ok().data("userList",userList);
    }

    /**
     * 根据id删除用户
     * @param id 要删除的用户id
     * @return 删除的状态
     */
    @ApiOperation("根据借阅号删除用户")
    @GetMapping("/delete/by/{id}")
    public ResultEntity deleteById(@ApiParam("用户借阅号") @PathVariable String id){
        boolean isRemove = userService.removeById(id);
        if (isRemove){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error().message("删除失败");
        }
    }

    /**
     * 修改用户信息
     * @param user 要修改的用户
     * @return 修改的状态
     */
    @ApiOperation("修改用户信息")
    @PostMapping("/update/user")
    public ResultEntity updateUser(@RequestBody User user){
        User updateUser = userService.getById(user.getBorrowNum());
        if (updateUser==null){
            return ResultEntity.error().message("该用户不存在");
        }
        boolean isUpdate = userService.updateById(user);
        if (isUpdate){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }

    /**
     * 根据openid查询用户
     * @param openId 用户的openid
     * @return 用户信息
     */
    @ApiOperation("根据openid查询用户")
    @GetMapping("/getUser/by/{openId}")
    public ResultEntity getUserByBorrowNum(@ApiParam("用户的openid")@PathVariable String openId){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("openid", openId);
        User user = userService.getOne(wrapper);
        if(user==null){
            return ResultEntity.error().code(ResultCode.ID_NOT_EXIST).message("借阅号不存在");
        }
        return ResultEntity.ok().data("user",user);
    }
}

