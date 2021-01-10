package com.tcu.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的openid
     */
    @ApiModelProperty(value = "用户的openid",required = true)
    private String openid;

    /**
     * 借阅号
     */
    @ApiModelProperty(value = "借阅号",required = true)
    @TableId(value = "borrow_num", type = IdType.ID_WORKER_STR)
    private String borrowNum;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称",required = true)
    private String nickname;


    /**
     * 用户手机号
     */
    @ApiModelProperty("用户手机号")
    private String phone;

    /**
     * 年级
     */
    @ApiModelProperty("年级")
    private String grade;

    /**
     * 专业
     */
    @ApiModelProperty("专业")
    private String profession;
}
