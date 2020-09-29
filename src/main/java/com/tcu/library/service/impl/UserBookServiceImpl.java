package com.tcu.library.service.impl;

import com.tcu.library.entity.UserBook;
import com.tcu.library.mapper.UserBookMapper;
import com.tcu.library.service.UserBookService;
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
public class UserBookServiceImpl extends ServiceImpl<UserBookMapper, UserBook> implements UserBookService {

}
