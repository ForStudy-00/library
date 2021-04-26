package com.tcu.library.entity;

import lombok.Data;

/**
 * @Author: yjn
 * @Date: 2021/4/14 15:45
 */
@Data
public class WxBackEntity {

    private String openid;
    private String sessionKey;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
