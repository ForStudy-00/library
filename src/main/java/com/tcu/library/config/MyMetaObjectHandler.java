package com.tcu.library.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @Author: yjn
 * @Date: 2020/10/5 18:02
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("status", "0", metaObject);
        this.setFieldValByName("number", "0", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
