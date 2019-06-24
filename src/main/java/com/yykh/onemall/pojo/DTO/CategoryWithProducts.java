package com.yykh.onemall.pojo.DTO;

import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.Product;
import lombok.Data;

import java.util.List;

/**
 * @Author：yykh
 * @Descripton:
 */

@Data
public class CategoryWithProducts {
    private Category category;
    private List<Product> products;
    private List<List<Product>> productsByRow;
}
