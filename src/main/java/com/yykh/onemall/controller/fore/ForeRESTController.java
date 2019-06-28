package com.yykh.onemall.controller.fore;

import com.github.pagehelper.PageInfo;
import com.yykh.onemall.Comparator.*;
import com.yykh.onemall.pojo.*;
import com.yykh.onemall.pojo.DTO.CategoryWithProducts;
import com.yykh.onemall.service.*;
import com.yykh.onemall.utils.Result;
import com.yykh.onemall.utils.SessionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author：yykh
 * @Descripton:
 */

@RestController
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @GetMapping("/forehome")
    public Object home() {
        List<CategoryWithProducts> cs= categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        return cs;
    }

    @PostMapping("/foreregister")
    public Object register(@RequestBody User user) {
        String name =  user.getName();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);

        boolean exist = userService.isExist(name);

        if(exist){
            String message ="用户名已经被使用,不能使用";
            return Result.fail(message);
        }

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String algorithmName = "md5";

        String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);

        userService.add(user);

        return Result.success();
    }

    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name =  userParam.getName();
        name = HtmlUtils.htmlEscape(name);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, userParam.getPassword());
        try {
            subject.login(token);
            User user = userService.getByName(name);
            session.setAttribute("user", user);
            return Result.success();
        } catch (AuthenticationException e) {
            String message ="账号密码错误";
            return Result.fail(message);
        }

    }

    @GetMapping("/forelogout")
    public String logout( ) {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            subject.logout();
        return "redirect:home";
    }

    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid) {
        Product product = productService.get(pid);
        product.getCategory().setName(categoryService.get(product.getCategory().getId()).getName());
        List<ProductImage> productSingleImages = productImageService.listSingleProductImages(pid);
        List<ProductImage> productDetailImages = productImageService.listDetailProductImages(pid);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);
        List<PropertyValue> pvs = propertyValueService.list(product);
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);

        Map<String,Object> map= new HashMap<>();
        map.put("product", product);
        map.put("pvs", pvs);
        map.put("reviews", reviews);

        return Result.success(map);
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin() {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            return Result.success();
        else
            return Result.fail("未登录");
    }

    @GetMapping("forecategory/{cid}")
    public Object category(@PathVariable int cid,String sort) {
        CategoryWithProducts c =new CategoryWithProducts();
        c.setCategory(categoryService.get(cid));
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
            }
        }

        return c;
    }

    @PostMapping("foresearch")
    public Object search( String keyword){
        if(null==keyword)
            keyword = "";
        PageInfo<Product> ps= productService.search(keyword,0,20);
        productImageService.setFirstProductImages(ps);
        productService.setSaleAndReviewNumber(ps);
        return ps;
    }

    @GetMapping("forebuyone")
    public Object buyone(int pid, int num, HttpSession session) {
        return buyoneAndAddCart(pid,num,session);
    }

    private int buyoneAndAddCart(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        productImageService.setFirstProductImage(product);
        User user =(User)session.getAttribute("user");
        ShoppingCart shoppingCart = SessionUtils.getShoppingCart(session);
        List<OrderItem> orderItems = shoppingCart.getOrderItems();
        int flat= 0;
        int oiid= 0;
        for(OrderItem oi :orderItems){
            if(oi.getProduct().getId() == pid){
                oi.setNumber(oi.getNumber()+num);
                shoppingCart.setTotal(shoppingCart.getTotal()+product.getPromotePrice()*num);
                orderItemService.update(oi);
                oiid=oi.getId();
                flat++;
            }
        }
        if(flat ==0){
            OrderItem oi = new OrderItem(user, product, num);
            shoppingCart.getOrderItems().add(oi);
            orderItemService.add(oi);
            oiid=oi.getId();
        }
        SessionUtils.updateCartTotalItemNumber(session,num);

        return oiid;
    }

    @GetMapping("forebuy")
    public Object buy(String[] oiid,HttpSession session){
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi= orderItemService.get(id);
            oi.setProduct(productService.get(oi.getProduct().getId()));
            total +=oi.getProduct().getPromotePrice()*oi.getNumber();
            orderItems.add(oi);
        }
        productImageService.setFirstProdutImagesOnOrderItems(orderItems);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setOrderItems(orderItems);
        shoppingCart.setTotal(total);
        session.setAttribute("orderItemsReadyToBeBought", shoppingCart);
        return Result.success(shoppingCart);
    }

    @GetMapping("foreaddCart")
    public Object addCart(int pid, int num, HttpSession session) {
        buyoneAndAddCart(pid,num,session);
        return Result.success();
    }

    @GetMapping("forecart")
    public Object cart(HttpSession session) {
        ShoppingCart shoppingCart = SessionUtils.getShoppingCart(session);
        return shoppingCart;
    }

    @GetMapping("forechangeOrderItem")
    public Object changeOrderItem( HttpSession session, int pid, int num) {
        ShoppingCart shoppingCart = SessionUtils.getShoppingCart(session);
        List<OrderItem> orderItems = shoppingCart.getOrderItems();
        for (OrderItem oi : orderItems) {
            if(oi.getProduct().getId()==pid){
                int oldNumber = oi.getNumber();
                oi.setNumber(num);
                int change = num -oldNumber;
                orderItemService.update(oi);
                SessionUtils.updateCartTotalItemNumber(session,change);
                break;
            }
        }
        return Result.success();
    }

    @GetMapping("foredeleteOrderItem")
    public Object deleteOrderItem(HttpSession session,int oiid){
        orderItemService.delete(oiid);
        return Result.success();
    }

    @GetMapping("foreCartTotalItemNumber")
    public int getCartTotalItemNumber(HttpSession session){
        return SessionUtils.getCartTotalItemNumber(session);

    }

    @PostMapping("forecreateOrder")
    public Object createOrder(@RequestBody Order order,HttpSession session){
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderService.WAIT_PAY);
        ShoppingCart orderItemsReadyToBeBought = (ShoppingCart) session.getAttribute("orderItemsReadyToBeBought");
        List<OrderItem> ois= orderItemsReadyToBeBought.getOrderItems();
        float total =orderItemsReadyToBeBought.getTotal();
        orderService.add(order,ois,session);
        Map<String,Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);
        return Result.success(map);
    }

    @GetMapping("forebought")
    public Object bought(HttpSession session) {
        List<Order> os= orderService.listByUserWithoutDelete(user);
        return os;
    }

    @GetMapping("forepayed")
    public Object payed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.WAIT_DELIVERY);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }

    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        o.setTotal(orderItemService.calculateTotalForOrderItems(o.getOrderItems()));
        return o;
    }

    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.DELETE);
        orderService.update(o);
        return Result.success();
    }

    @GetMapping("forereview")
    public Object review(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p);
        productService.setSaleAndReviewNumber(p);
        Map<String,Object> map = new HashMap<>();
        map.put("p", p);
        map.put("o", o);
        map.put("reviews", reviews);

        return Result.success(map);
    }

    @PostMapping("foredoreview")
    public Object doreview( HttpSession session,int oid,int pid,String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.FINISN);
        orderService.update(o);
        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);
        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date());
        review.setUser(user);
        reviewService.add(review);
        return Result.success();
    }

    @GetMapping("foreorderConfirmed")
    public Object orderConfirmed( int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.WAIT_REVIEW);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return Result.success();
    }


}
