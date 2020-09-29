package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class UserBook implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 借阅号
     */
    private String borrowNum;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单编号
     */
      @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;

    /**
     * 书籍编号
     */
    private String bookId;

    /**
     * 已逾期:0 已预约:1 借阅中:2 已归还:3
     */
    private Integer status;

    /**
     * 预计归还时间
     */
    private Date returnTime;

    /**
     * 取书时间
     */
    private Date takeTime;


}
