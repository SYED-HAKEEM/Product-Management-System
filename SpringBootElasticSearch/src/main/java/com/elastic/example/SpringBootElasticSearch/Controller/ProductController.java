package com.elastic.example.SpringBootElasticSearch.Controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elastic.example.SpringBootElasticSearch.Entity.Product;
import com.elastic.example.SpringBootElasticSearch.Service.ElasticSearchService;
import com.elastic.example.SpringBootElasticSearch.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apis/")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ElasticSearchService elasticSearchService;

    @GetMapping("findAllProducts")
    Iterable<Product> findAllProducts(){
        return productService.getProducts();
    }
    @PostMapping("insertProduct")
    Product insertProduct(@RequestBody Product product){
        return productService.insertProduct(product);
    }
    @GetMapping("matchAllProducts")
    String matchAllProducts() throws IOException {
        SearchResponse<Map> searchResponse = elasticSearchService.matchAllServices();
        System.out.println(searchResponse.hits().hits().toString());
        return searchResponse.hits().hits().toString();
    }
    @GetMapping("matchAllProductServices")
    List<Product> matchAllProductsServices() throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.matchAllProductServices();
        System.out.println(searchResponse.hits().hits().toString());
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        return productList;
    }
    @GetMapping("matchAllProductServices/{fieldValue}")
    List<Product> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.matchProductWithNameFieldServices(fieldValue);
        System.out.println(searchResponse.hits().hits().toString());
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        return productList;
    }
    /*@GetMapping("matchAllProductServices/{approximateProductName}")
    List<Product> fuzzySearch(@PathVariable String approximateProductName) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.fuzzySearchServices(approximateProductName);
        System.out.println(searchResponse.hits().hits().toString());
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        return productList;
    }*/
    /*@GetMapping("matchAllProductServices/{partialProductName}")
    List<String> autoSuggestProductSearch(@PathVariable String partialProductName) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.autoSuggestServices(partialProductName);
        System.out.println(searchResponse.hits().hits().toString());
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        List<String> productNameList = new ArrayList<>();
        for(Product product : productList) {
            productNameList.add(product.getName());
        }
        return productNameList;
    }*/
    @GetMapping("matchAllProductServices/{productName}/{quantity}")
    List<Product> matchBoolQuery(@PathVariable String productName, @PathVariable Integer quantity) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.boolQueryServices(productName, quantity);
        System.out.println(searchResponse.hits().hits().toString());
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit : hitList){
            productList.add(hit.source());
        }
        return productList;
    }
}
