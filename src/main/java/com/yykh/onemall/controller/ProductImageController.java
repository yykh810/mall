package com.yykh.onemall.controller;


import com.yykh.onemall.pojo.ProductImage;
import com.yykh.onemall.service.CategoryService;
import com.yykh.onemall.service.ProductImageService;
import com.yykh.onemall.service.ProductService;
import com.yykh.onemall.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductImageController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;

    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@RequestParam("type") String type, @PathVariable("pid") int pid) throws Exception {
        if(ProductImageService.TYPE_SINGLE.equals(type)) {
            List<ProductImage> singles =  productImageService.listSingleProductImages(pid);
            return singles;
        }
        else if(ProductImageService.TYPE_DETAIL.equals(type)) {
            List<ProductImage> details =  productImageService.listDetailProductImages(pid);
            return details;
        }
        else {
            return null;
        }
    }

    @PostMapping("/productImages")
    public Object add(@RequestParam("pid") int pid, @RequestParam("type") String type, MultipartFile image, HttpServletRequest request) throws Exception {
        ProductImage bean = getbean(pid, type);
        productImageService.add(bean);
        File file = getFile(bean,request);
        String fileName = file.getName();
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ProductImageService.TYPE_SINGLE.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        return bean;
    }



    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        ProductImage bean = productImageService.get(id);
        productImageService.delete(id);

        String folder = "img/";
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType()))
            folder +="productSingle";
        else
            folder +="productDetail";

        File  imageFolder= new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType())){
            String imageFolder_small= request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle= request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }

        return null;
    }

    /**
     * @Description:根据传入的ProductImage判断其type，完善其图片文件夹名。
     * @return
     */
    public File getFile(ProductImage bean, HttpServletRequest request){
        String folder = getFolderNameByType(bean);
        return new File(request.getServletContext().getRealPath(folder)+"\\"+bean.getId()+".jpg");
    }

    public String getFolderNameByType(ProductImage bean){
        String folder = "img/";
        if(ProductImageService.TYPE_SINGLE.equals(bean.getType())){
            folder +="productSingle";
        }
        else{
            folder +="productDetail";
        }
        return folder;
    }

    public ProductImage getbean(int pid, String type) {
        ProductImage bean = new ProductImage();
        bean.setType(type);
        bean.setPid(pid);
        return bean;
    }
}
