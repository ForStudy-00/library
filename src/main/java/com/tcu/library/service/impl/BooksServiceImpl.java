package com.tcu.library.service.impl;

import com.tcu.library.entity.Books;
import com.tcu.library.mapper.BooksMapper;
import com.tcu.library.service.BooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yjn
 * @since 2020-10-05
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {

}
