package com.tcu.library.service.impl;

import com.tcu.library.entity.Admin;
import com.tcu.library.mapper.AdminMapper;
import com.tcu.library.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.getAdminByUserName(username);
    }
}
