package com.tcu.library.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: yjn
 * @Date: 2020/10/5 18:02
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void insertFill(MetaObject metaObject) {
        //books
        this.setFieldValByName("status", 0, metaObject);
        this.setFieldValByName("number", 0, metaObject);
        //userBook
        this.setFieldValByName("createTime",new Date(), metaObject);
        this.setFieldValByName("userBookId", UUID.randomUUID().toString().replace("-", ""), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }
}
