package com.yykh.onemall.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author：yykh
 * @Descripton:
 */

@Data
public class Review {
    private int id;
    private User user;
    private Product product;
    private String content;
    private Date createDate;
}
