package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String id;

    /**
     * 书名
     */
    private String title;

    /**
     * 作者名
     */
    private String author;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 出版社
     */
    private String press;

    /**
     * 书籍封面地址
     */
    private String url;

    /**
     * 在库存:0 已借出:1
     */
    @TableField(fill = FieldFill.INSERT)
    private String status;

    /**
     * 图书类型
     */
    private String type;

    /**
     * 借阅次数
     */
    @TableField(fill = FieldFill.INSERT)
    private String number;


}
