package com.yykh.onemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yykh.onemall.mapper.ProductMapper;
import com.yykh.onemall.pojo.Category;
import com.yykh.onemall.pojo.DTO.CategoryWithProducts;
import com.yykh.onemall.pojo.OrderItem;
import com.yykh.onemall.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;

    private static ProductMapper productMapperTwo;

    @PostConstruct
    public void init(){
        productMapperTwo=productMapper;
    }

    public void add(Product bean) {
        productMapper.save(bean);
    }

    public void delete(int id) {
        productMapper.delete(id);
    }

    public  Product get(int id) {
        return productMapper.findOne(id);
    }

    public void update(Product bean) {
        productMapper.update(bean);
    }

    public PageInfo<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        PageHelper.startPage(start,size,"id");
        Page<Product> pr = productMapper.findAll(category);
        PageInfo<Product> page =new PageInfo(pr);
        return page;
    }

    public void fill(List<CategoryWithProducts> categoryWithProducts) {
        for (CategoryWithProducts categoryWithProduct : categoryWithProducts) {
            fill(categoryWithProduct);
        }
    }

    public void fill(CategoryWithProducts categoryWithProduct){
        List<Product> products=productMapper.listProductByCategory(categoryWithProduct.getCategory());
        productImageService.setFirstProductImages(products);
        categoryWithProduct.setProducts(products);
    }

    public void fillByRow(List<CategoryWithProducts> categoryWithProducts) {
        int productNumberEachRow = 8;
        for (CategoryWithProducts categoryWithProduct : categoryWithProducts) {
            List<Product> products = productMapper.listProductByCategory(categoryWithProduct.getCategory());
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                int size = i+productNumberEachRow;
                size= size>products.size()?products.size():size;
                List<Product> productsOfEachRow =products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            categoryWithProduct.setProductsByRow(productsByRow);
        }
    }

    public List<Product> listByCategory(Category category){
        return productMapper.findByCategoryOrderById(category);
    }

    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product);
        product.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(product);
        product.setReviewCount(reviewCount);

    }

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products)
            setSaleAndReviewNumber(product);
    }

    public void setSaleAndReviewNumber(PageInfo<Product> products) {
        for (Product product : products.getList())
            setSaleAndReviewNumber(product);
    }

    public PageInfo<Product> search(String keyword, int start, int size) {
        PageHelper.startPage(start,size,"id");
        Page<Product> pro =productMapper.findByNameLike("%"+keyword+"%");
        PageInfo<Product> page = new PageInfo<>(pro);
        return page;
    }

    public void fillProductAndFirstProductImageForOrderItems(List<OrderItem> orderItems){
        for ( OrderItem oi : orderItems ){
            Product product = get(oi.getProduct().getId());
            oi.setProduct(product);
            productImageService.setFirstProductImage(product);
        }

    }
}
