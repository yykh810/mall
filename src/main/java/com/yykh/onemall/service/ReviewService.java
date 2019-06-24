package com.yykh.onemall.service;

import com.yykh.onemall.mapper.ReviewMapper;
import com.yykh.onemall.pojo.Product;
import com.yykh.onemall.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;
    @Autowired
    ProductService productService;

    public void add(Review review) {
        reviewMapper.save(review);
    }

    public List<Review> list(Product product){
        List<Review> result =  reviewMapper.findByProductOrderByIdDesc(product);
        return result;
    }

    public int getCount(Product product) {
        return reviewMapper.countByProduct(product);
    }

}
