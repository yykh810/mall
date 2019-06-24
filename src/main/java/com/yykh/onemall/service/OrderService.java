package com.yykh.onemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.OrderMapper;
import com.yykh.onemall.pojo.Order;
import com.yykh.onemall.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

@Service
public class OrderService {
    public static final String WAIT_PAY = "waitPay";
    public static final String WAIT_DELIVERY = "waitDelivery";
    public static final String WAIT_CONFIRM = "waitConfirm";
    public static final String WAIT_REVIEW = "waitReview";
    public static final String FINISN = "finish";
    public static final String DELETE = "delete";

    @Autowired
    OrderMapper orderMapper;

    /**
     *
     * @param start
     * @param size
     * @param navigatePages（指定导航面板上的页面数量，暂未用到）
     * @return  不包括orderItem和product的order。
     */
    public PageInfo<Order> list(int start, int size, int navigatePages) {
        PageHelper.startPage(start,size,"id");
        Page<Order> or = orderMapper.findAll();
        PageInfo<Order> page =new PageInfo(or);
        return page;
    }

    /**
     *
     * @param oid
     * @return 不包括orderItem和product的order。
     */
    public Order get(int oid) {
        return orderMapper.findOne(oid);
    }

    /**
     *
     * @param bean
     * 更新order
     */
    public void update(Order bean) {
        orderMapper.save(bean);
    }

}
