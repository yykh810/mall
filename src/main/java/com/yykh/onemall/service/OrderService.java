package com.yykh.onemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.OrderMapper;
import com.yykh.onemall.pojo.Order;
import com.yykh.onemall.pojo.OrderItem;
import com.yykh.onemall.pojo.User;
import com.yykh.onemall.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
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
    @Autowired OrderItemService orderItemService;

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

    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public void add(Order order, List<OrderItem> ois, HttpSession session) {
        float total = 0;
        add(order);
        if(false)
            throw new RuntimeException();
        for (OrderItem oi: ois) {
            oi.setOid(order.getId());
            orderItemService.update(oi);
        }
        SessionUtils.updateShoppingCartAndOrderItemsReadyToBeBought(session,ois);
    }
    public void add(Order order) {
        orderMapper.add(order);
    }

    public List<Order> listByUserWithoutDelete(User user) {
        List<Order> orders = listByUserWithNotDelete(user);
        orderItemService.fill(orders);
        return orders;
    }

    public List<Order> listByUserWithNotDelete(User user) {
        return orderMapper.findByUserAndReverseStatusOrderByIdDesc(user, OrderService.DELETE);
    }
}
