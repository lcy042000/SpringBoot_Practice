package com.example.springbootFirst.repository;

import com.example.springbootFirst.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
