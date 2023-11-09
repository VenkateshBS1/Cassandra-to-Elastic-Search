package com.ivoyant.MappingCassandraToElasticSearch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivoyant.MappingCassandraToElasticSearch.entity.Students1;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ElasticStudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticStudentService.class);

    @Autowired
    RestHighLevelClient client;

    public void createStudent(Students1 students1) throws IOException {
        if (students1 != null) {
            ObjectMapper mapper = new ObjectMapper();
            String studentJson = mapper.writeValueAsString(students1);
            IndexRequest request = new IndexRequest("students1");
            request.id(String.valueOf(students1.getId()));
            request.source(studentJson,XContentType.JSON );
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            if (response.getResult() == DocWriteResponse.Result.CREATED) {
                LOGGER.info("Document created in Customer Index with ID : {}", students1.getId());
            } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("Document updated in index ");
            }
        }
    }

    public Students1 getStudentObject(String id) throws IOException {
        String fieldName="id";
        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(fieldName, id);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(matchQueryBuilder);
        SearchRequest searchRequest = new SearchRequest("students1");
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        String sourceAsString = "";
        for (SearchHit hit : searchHits) {
            if (hit != null) {
                sourceAsString = hit.getSourceAsString();
            } else {
                LOGGER.error("Zero SearchHits for id : {}", id);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(sourceAsString, Students1.class);
    }



}
