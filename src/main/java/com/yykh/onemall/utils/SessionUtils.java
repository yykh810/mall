package com.yykh.onemall.utils;

import com.yykh.onemall.pojo.OrderItem;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.ShoppingCart;
import com.yykh.onemall.pojo.User;
import com.yykh.onemall.service.OrderItemService;
import com.yykh.onemall.service.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */
//为了解决在static方法中调用field
@Component
public class SessionUtils {

    private static final Logger logger = Logger.getLogger(SessionUtils.class);

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;

    private static OrderItemService orderItemServiceTwo;
    private static ProductService productServiceTwo;

    @PostConstruct
    public void init(){
        orderItemServiceTwo = orderItemService ;
        productServiceTwo = productService ;
    }
    /**
     * @param session
     * @return 原有的shoppingcart或者新建的shoppingcart
     */
    public static ShoppingCart getShoppingCart(HttpSession session){
        ShoppingCart shoppingCart = null;
        try{
            shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
            if(shoppingCart != null){
                return shoppingCart;
            }
            else{
                SessionUtils sessionUtils = new SessionUtils();
                User user = (User) session.getAttribute("user");
                List<OrderItem> orderItems = orderItemServiceTwo.getOrderItemsWithNoOrderByUser(user);
                productServiceTwo.fillProductAndFirstProductImageForOrderItems(orderItems);
                float total = orderItemServiceTwo.calculateTotalForOrderItems(orderItems);
                shoppingCart = new ShoppingCart(orderItems,total);//创建新的购物车模型
                session.setAttribute("shoppingCart", shoppingCart);
            }
        }catch(Exception e){
            logger.error("SessionUtils.getShoppingCart", e);
        }
        return shoppingCart;
    }

    public static void updateShoppingCartAndOrderItemsReadyToBeBought(HttpSession session,List<OrderItem> ois) {
        ShoppingCart shoppingCart =SessionUtils.getShoppingCart(session);
        for(OrderItem oi :ois){
            int id = oi.getId();
            delete(shoppingCart,id);
        }
        session.setAttribute("orderItemsReadyToBeBought",null);
        session.setAttribute("shoppingCart",shoppingCart);
    }

    public static void delete(ShoppingCart shoppingCart,int id){
        List<OrderItem> ois = shoppingCart.getOrderItems();
        for(int i=0;i<ois.size();i++){
            OrderItem oi = ois.get(i);
            if(oi.getId()==id){
                shoppingCart.setTotal(shoppingCart.getTotal() - oi.getNumber()*oi.getProduct().getPromotePrice());
                ois.remove(i);
            }
        }
    }

    /**
     * @param session
     * @return 原有的cartTotalItemNumber或者新建的cartTotalItemNumber
     */
    public static int getCartTotalItemNumber(HttpSession session){
        int cartTotalItemNumber =0;
        try{
            if (session.getAttribute("cartTotalItemNumber") != null){
                cartTotalItemNumber = (int) session.getAttribute("cartTotalItemNumber"); }
            else{
                session.setAttribute("cartTotalItemNumber",0);
                cartTotalItemNumber=0;
            }
        }
        catch(Exception e) {
            logger.error("SessionUtils.getShoppingCart", e);
        }
        return cartTotalItemNumber;
        }

    /**
     * @param session 当前会话 , change 数量变动额
     * @return void
     */
    public static void updateCartTotalItemNumber(HttpSession session,int num ) {
        int oldCartTotalItemNumber = getCartTotalItemNumber(session);
        session.setAttribute("cartTotalItemNumber",oldCartTotalItemNumber + num);
    }
}
