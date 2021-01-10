package com.tcu.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yjn
 * @Date: 2021/1/1 19:26
 */
@Data
public class PendTakenBook implements Serializable {

    private String title;
    private String url;
    private String author;
    private String orderId;
    private String bookId;
    /**
     * 预计归还时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    /**
     * 取书时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date takeTime;
    private int status;
}
