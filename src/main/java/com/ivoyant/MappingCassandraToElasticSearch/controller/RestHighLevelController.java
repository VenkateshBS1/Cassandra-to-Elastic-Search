package com.ivoyant.MappingCassandraToElasticSearch.controller;

import com.ivoyant.MappingCassandraToElasticSearch.entity.Students1;
import com.ivoyant.MappingCassandraToElasticSearch.service.ElasticStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("students/")
public class RestHighLevelController {
    @Autowired
    ElasticStudentService elasticStudentService;

    @PostMapping("createStudent")
    public ResponseEntity<String> createStudent(@RequestBody Students1 students1) throws IOException {
        elasticStudentService.createStudent(students1);
        return ResponseEntity.ok("Successfully created");
    }

    @GetMapping("getStudent/{id}")
    public Students1 getStudent(
            @PathVariable String id) throws IOException {
        return elasticStudentService.getStudentObject(id);
    }


}
