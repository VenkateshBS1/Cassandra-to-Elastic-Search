package com.ivoyant.MappingCassandraToElasticSearch.entity;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class Students1  {

    private UUID id = Uuids.timeBased();
    private String name;
    private Integer grade;
    private AddressUDT address;
    private List<SubjectUDT> subjects;
}
