package com.elastic.example.SpringBootElasticSearch.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.util.ObjectBuilder;
import com.elastic.example.SpringBootElasticSearch.Entity.Product;
import com.elastic.example.SpringBootElasticSearch.Util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
@Service
public class ElasticSearchService {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public SearchResponse<Map> matchAllServices() throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s->s.query(supplier.get()), Map.class);
        System.out.println("Elastic Search Query is : " +supplier.get().toString());
        return searchResponse;
    }
    public SearchResponse<Product> matchAllProductServices() throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplier();
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println("Elastic Search Query is : " +supplier.get().toString());
        return searchResponse;
    }
    public SearchResponse<Product> matchProductWithNameFieldServices(String fieldName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithNameField(fieldName);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println("Elastic Search Query is : " +supplier.get().toString());
        return searchResponse;
    }
    public SearchResponse<Product> fuzzySearchServices(String approximateProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierFuzzyQuery(approximateProductName);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println("Elastic Search Fuzzy Query is : " +supplier.get().toString());
        return searchResponse;
    }
    public SearchResponse<Product> autoSuggestServices(String partialProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierAutoSuggestMatchQuery(partialProductName);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println("Elastic Search Auto Suggest Query is : " +supplier.get().toString());
        return searchResponse;
    }
    public SearchResponse<Product> boolQueryServices(String productName, Integer quantity) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierQueryForBoolQuery(productName, quantity);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s->s.index("products").query(supplier.get()), Product.class);
        System.out.println("Elastic Search Bool Query is : " +supplier.get().toString());
        return searchResponse;
    }
}
