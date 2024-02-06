package com.demo.application.publicationservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "publication")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Publication {
    @Id
    private String id;
    private String name;
    private String type;
    private String author;
    private String publisher;
    private BigDecimal price;
}
