package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * <p>
 *
 * </p>
 *
 * @author yjn
 * @since 2020-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Want implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 借阅号
     */
    private BigInteger borrowNum;

    /**
     * 图书编号
     */
    private String bookId;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

}
