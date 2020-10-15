package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的openid
     */
    private String openid;

    /**
     * 借阅号
     */
    @TableId(value = "borrow_num", type = IdType.ID_WORKER_STR)
    private String borrowNum;

    /**
     * 用户昵称
     */
    private String nickname;


}
