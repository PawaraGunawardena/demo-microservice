package com.demo.application.publicationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationResponse {
    @Id
    private String id;
    private String name;
    private String type;
    private String author;
    private String publisher;
    private BigDecimal price;
}
