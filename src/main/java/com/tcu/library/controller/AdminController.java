package com.tcu.library.controller;


import com.tcu.library.entity.Admin;
import com.tcu.library.service.AdminService;
import com.tcu.library.uitls.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@RestController
@RequestMapping("/library/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 根据用户名查询admin
     * @param username
     * @return
     */
    @PostMapping("/login/{username}")
    public ResultEntity login(@PathVariable String username) {
        Admin admin = adminService.getAdminByUserName(username);
        System.out.println(admin.toString());
        return ResultEntity.ok().data("admin", admin);
    }

}

