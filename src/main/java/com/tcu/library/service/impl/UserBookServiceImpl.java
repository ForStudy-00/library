package com.tcu.library.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tcu.library.entity.UserBook;
import com.tcu.library.mapper.UserBookMapper;
import com.tcu.library.service.UserBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @Resource
    private UserBookMapper userBookMapper;

    @Override
    public ArrayList getDetailsPendingTaken(String borrowNum) {
        return userBookMapper.getDetailsPendingTaken(borrowNum);
    }

    @Override
    public List<HashMap<String, Object>> getReturnTime() {
        return userBookMapper.getReturnTime();
    }

    @Override
    public void updataStatusToThree(String id) {
        userBookMapper.updataStatusToThree(id);
    }
}
