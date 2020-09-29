package com.tcu.library.service.impl;

import com.tcu.library.entity.User;
import com.tcu.library.mapper.UserMapper;
import com.tcu.library.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
