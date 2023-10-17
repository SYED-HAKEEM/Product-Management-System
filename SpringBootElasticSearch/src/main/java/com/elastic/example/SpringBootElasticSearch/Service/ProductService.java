package com.elastic.example.SpringBootElasticSearch.Service;

import com.elastic.example.SpringBootElasticSearch.Entity.Product;
import com.elastic.example.SpringBootElasticSearch.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public Iterable<Product> getProducts(){
        return productRepo.findAll();
    }
    public Product insertProduct(Product product){
        return productRepo.save(product);
    }
    public Product updateProductById(Product product, int id){
        Product product1 = productRepo.findById(id).get();
        product1.setPrice(product.getPrice());
        return product1;
    }
    public void deleteProductById(int id){
        productRepo.deleteById(id);
    }
}
