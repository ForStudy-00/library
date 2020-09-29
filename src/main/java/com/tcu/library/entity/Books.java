package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@Data
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
    @TableField("Introduction")
    private String Introduction;

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
    private Integer status;

    /**
     * 图书类型
     */
    private String type;


}
