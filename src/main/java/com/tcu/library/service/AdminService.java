package com.tcu.library.service;

import com.tcu.library.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
public interface AdminService extends IService<Admin> {
    /**
     * 通过用户名获取admin
     * @param username
     * @return
     */
   Admin getAdminByUserName(String username);
}
