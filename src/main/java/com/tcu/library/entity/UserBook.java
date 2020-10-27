package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yjn
 * @since 2020-09-29
 */
@Data
@TableName("user_book")
@EqualsAndHashCode(callSuper = false)
public class UserBook implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 借阅号
     */
    private String borrowNum;

    /**
     * 订单创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 订单编号
     */
    @TableField(fill = FieldFill.INSERT)
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String userBookId;

    /**
     * 书籍编号
     */
    private String bookId;

    /**
     * 已预约:0 借阅中:1 已归还:2 已逾期:3
     */
    @TableField(fill = FieldFill.INSERT)
    private int status;

    /**
     * 预计归还时间
     */
    private Date returnTime;

    /**
     * 取书时间
     */
    private Date takeTime;


}
