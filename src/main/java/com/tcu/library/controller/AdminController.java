package com.tcu.library.controller;


import com.tcu.library.entity.Admin;
import com.tcu.library.service.AdminService;
import com.tcu.library.uitls.ResultCode;
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
@CrossOrigin
@RestController
@RequestMapping("/library/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 根据用户名查询admin
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public ResultEntity login(@RequestBody Admin admin) {
        Admin adminByUserName = adminService.getAdminByUserName(admin.getUsername());
        if (adminByUserName.getPassword().equals(admin.getPassword())){
        return ResultEntity.ok();
        }else {
            return ResultEntity.error().message("密码错误").code(ResultCode.LOGIN_ERROR);
        }
    }

    /**
     * 根据id修改admin
     * @param admin
     * @return
     */
    @PostMapping("/update")
    public ResultEntity updateAdmin(@RequestBody Admin admin){
        boolean isUpdate = adminService.updateById(admin);
        if (isUpdate){
            return ResultEntity.ok();
        }else {
            return ResultEntity.error();
        }
    }
}

