package com.yykh.onemall.controller.fore;

import com.github.pagehelper.PageInfo;
import com.yykh.onemall.Comparator.*;
import com.yykh.onemall.pojo.*;
import com.yykh.onemall.pojo.DTO.CategoryWithProducts;
import com.yykh.onemall.service.*;
import com.yykh.onemall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        userService.add(user);

        return Result.success();
    }

    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name =  userParam.getName();
        name = HtmlUtils.htmlEscape(name);

        User user =userService.get(name,userParam.getPassword());
        if(null==user){
            String message ="账号密码错误";
            return Result.fail(message);
        }
        else{
            session.setAttribute("user", user);
            return Result.success();
        }
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

}
