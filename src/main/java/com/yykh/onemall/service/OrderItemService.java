package com.yykh.onemall.service;

import com.yykh.onemall.mapper.OrderItemMapper;
import com.yykh.onemall.pojo.Order;
import com.yykh.onemall.pojo.OrderItem;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */
@Service
public class OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    /**
     *
     * @param orders
     * 调用另一个fill方法
     */
    public void fill(List<Order> orders) {
        for (Order order : orders)
            fill(order);
    }

    /**
     *
     * @param order
     * 对order进行orderItem和product的补全
     */
    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi :orderItems) {
            Product product =productService.get(oi.getProduct().getId());
            int number = oi.getNumber();
            total+=number*(product.getPromotePrice());
            totalNumber+= number;
            oi.setNumber(number);
            oi.setProduct(product);
            productImageService.setFirstProductImage(product);
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    public List<OrderItem> listByOrder(Order order) {
        return orderItemMapper.findByOrderOrderByIdDesc(order);
    }

    public List<OrderItem> listByProduct(Product product) {
        return orderItemMapper.findByProduct(product);
    }

    public int getSaleCount(Product product) {
        List<OrderItem> ois =listByProduct(product);
        int result =0;
        for (OrderItem oi : ois) {
            if(0!=oi.getOid())
                if(0!= oi.getOid() && null!=orderService.get(oi.getOid()).getPayDate())
                    result+=oi.getNumber();
        }
        return result;
    }

    public List<OrderItem> getOrderItemsWithNoOrderByUser(User user) {
        return orderItemMapper.findByUserAndOrderIsNull(user);
    }

    /**
     * 注意！只更新其中的number。
     * @param oi
     */
    public void update(OrderItem oi) {
        orderItemMapper.update(oi);
    }

    public void add(OrderItem oi) {
        orderItemMapper.add(oi);
    }

    public OrderItem get(int id) {
        return orderItemMapper.findById(id);
    }

    public void delete(int oiid) {
        orderItemMapper.delete(oiid);
    }

    public  float calculateTotalForOrderItems(List<OrderItem> orderItems){
        float total = 0;
        for( OrderItem oi: orderItems){
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

}