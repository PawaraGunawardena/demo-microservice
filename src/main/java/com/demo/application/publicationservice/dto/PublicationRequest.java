package com.demo.application.publicationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationRequest {
    private String name;
    private String type;
    private String author;
    private String publisher;
    private BigDecimal price;
}
