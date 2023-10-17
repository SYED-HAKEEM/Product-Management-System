package com.elastic.example.SpringBootElasticSearch.Util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ElasticSearchUtil {

    public static Supplier<Query> supplier(){
        Supplier<Query> supplier = () ->Query.of(q->q.matchAll(matchAllQuery()));
        return supplier;
    }

    public static MatchAllQuery matchAllQuery(){
        val matchAllQuery = new MatchAllQuery.Builder();
        return  matchAllQuery.build();
    }
    public static Supplier<Query> supplierWithNameField(String fieldValue){
        Supplier<Query> supplier = () ->Query.of(q->q.match(matchWithNameFieldlQuery(fieldValue)));
        return supplier;
    }

    public static MatchQuery matchWithNameFieldlQuery(String fieldValue){
        val matchWithNameFieldQuery = new MatchQuery.Builder();
        return  matchWithNameFieldQuery.field("name").query(fieldValue).build();
    }
    public static Supplier<Query> createSupplierFuzzyQuery(String approximateProductName){
        Supplier<Query> supplier = () ->Query.of(q->q.fuzzy(createFuzzyQuery(approximateProductName)));
        return supplier;
    }

    public static FuzzyQuery createFuzzyQuery(String approximateProductName){
        val fuzzyQuery = new FuzzyQuery.Builder();
        return  fuzzyQuery.field("name").value(approximateProductName).build();
    }
    public static Supplier<Query> createSupplierAutoSuggestMatchQuery(String partialProductName){
        Supplier<Query> supplier = () ->Query.of(q->q.match(createAutoSuggestMatchQuery(partialProductName)));
        return supplier;
    }

    public static MatchQuery createAutoSuggestMatchQuery(String partialProductName){
        val autoSuggestQuery = new MatchQuery.Builder();
        return  autoSuggestQuery.field("name").query(partialProductName).analyzer("standard").build();
    }
    public static Supplier<Query> supplierQueryForBoolQuery(String productName, Integer quantity){
        Supplier<Query> supplier = () ->Query.of(q->q.bool(boolQuery(productName, quantity)));
        return supplier;
    }
    public static BoolQuery boolQuery(String productName, Integer quantity){
        val boolQuery = new BoolQuery.Builder();
        return boolQuery.filter(termQuery(productName)).must(matchQuery(quantity)).build();
    }
    public static List<Query> termQuery(String productName){
        final List<Query> terms = new ArrayList<>();
        val termQuery = new TermQuery.Builder();
        terms.add(Query.of(q->q.term(termQuery.field("name").value(productName).build())));
        return terms;
    }
    public static List<Query> matchQuery(Integer quantity){
        final List<Query> matches = new ArrayList<>();
        val matchQuery = new MatchQuery.Builder();
        matches.add(Query.of(q->q.match(matchQuery.field("quantity").query(quantity).build())));
        return matches;
    }
}
