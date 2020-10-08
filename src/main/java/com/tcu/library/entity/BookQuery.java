package com.tcu.library.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: yjn
 * @Date: 2020/10/6 20:16
 */
@Data
public class BookQuery implements Serializable {
    private String searchTitle;
    private String searchAuthor;
}
