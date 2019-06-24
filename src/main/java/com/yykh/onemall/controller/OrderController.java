package com.yykh.onemall.controller;

import com.github.pagehelper.PageInfo;
import com.yykh.onemall.pojo.Order;
import com.yykh.onemall.service.OrderItemService;
import com.yykh.onemall.service.OrderService;
import com.yykh.onemall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

/**
 * @Author：yykh
 * @Descripton:
 */

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    /**
     *
     * @param start
     * @param size
     * @return 分页后的order，包括orderItem里的product信息。
     * @throws Exception
     */
    @GetMapping("/orders")
    public PageInfo<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        PageInfo<Order> page =orderService.list(start, size, 5);
        orderItemService.fill(page.getList());
        return page;
    }

    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) throws IOException {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.WAIT_CONFIRM);
        orderService.update(o);
        return Result.success();
    }
}
