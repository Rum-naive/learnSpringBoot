package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Article {

    private Long id;
    private String author;
    private String title;
    private String content;
    private String createTime;
    private List<Reader> reader;

}