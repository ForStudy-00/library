package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigInteger;
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
    private BigInteger borrowNum;

    private String bookName;

    /**
     * 订单创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 订单编号
     */
//    @TableField(fill = FieldFill.INSERT)
    @TableId(value = "id", type = IdType.AUTO)
    private BigInteger userBookId;

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
//     @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /**
     * 取书时间
     */
     @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeTime;


}
