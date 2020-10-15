package com.tcu.library.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjn
 * @since 2020-10-05
 */
@Data
@TableName("books")
@EqualsAndHashCode(callSuper = false)
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 书籍编号
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    @ExcelProperty(index = 0)
    private String id;

    /**
     * 书名
     */
    @ExcelProperty(index = 1)
    private String title;

    /**
     * 作者名
     */
    @ExcelProperty(index = 2)
    private String author;

    /**
     * 简介
     */
    @ExcelProperty(index = 3)
    private String introduction;

    /**
     * 出版社
     */
    @ExcelProperty(index = 4)
    private String press;

    /**
     * 书籍封面地址
     */
    @TableField(fill = FieldFill.INSERT)
    private String url;

    /**
     * 在库存:0 已借出:1
     */
    @TableField(fill = FieldFill.INSERT)
    private String status;

    /**
     * 图书类型
     */
    @ExcelProperty(index = 5)
    private String type;

    /**
     * 借阅次数
     */
    @TableField(fill = FieldFill.INSERT)
    private String number;


}
