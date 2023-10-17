package com.elastic.example.SpringBootElasticSearch.Repository;

import com.elastic.example.SpringBootElasticSearch.Entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepo extends ElasticsearchRepository<Product,Integer> {
}
