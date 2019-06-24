package com.yykh.onemall.pojo;

import com.yykh.onemall.service.OrderService;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

@Data
public class Order {
    private int id;
    private int uid;
    private float total;
    private int totalNumber;
    private List<OrderItem> orderItems;
    private String orderCode;
    private String address;
    private String post;
    private String receiver;
    private String mobile;
    private String userMessage;
    private Date createDate;
    private Date payDate;
    private Date deliveryDate;
    private Date confirmDate;
    private String status;
    private String statusDesc;

    public String getStatusDesc(){
        String desc ="未知";
        switch(status){
            case OrderService.WAIT_PAY:
                desc="待付款";
                break;
            case OrderService.WAIT_DELIVERY:
                desc="待发货";
                break;
            case OrderService.WAIT_CONFIRM:
                desc="待收货";
                break;
            case OrderService.WAIT_REVIEW:
                desc="等评价";
                break;
            case OrderService.FINISN:
                desc="完成";
                break;
            case OrderService.DELETE:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }
}
