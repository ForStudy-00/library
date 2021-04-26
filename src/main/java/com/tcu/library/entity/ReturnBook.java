package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ReturnBook implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
      @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 图书编号
     */
    private String bookId;

    /**
     * 借阅号
     */
    private BigInteger borrowNum;

    /**
     * 还书状态 0:正常归还 1:延期归还
     */
    private Integer state;

    /**
     * 还书时间
     */
//    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /**
     * 理由
     */
    private String reason;

    /**
     * 是否提交还书申请 0未提交 1已提交
     */
    //private int returnStatus;

}
