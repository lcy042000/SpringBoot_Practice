package com.example.springbootFirst.api;

import com.example.springbootFirst.DTO.ArticleForm;
import com.example.springbootFirst.entity.Article;
import com.example.springbootFirst.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleRepository articleRepository;

    //GET
    @GetMapping("/news/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/news/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    //POST
    @PostMapping("/news/articles")
    public Article create(@RequestBody ArticleForm dto){
        Article article = dto.toEntity();

        return articleRepository.save(article);
    }

    //PATH
    @PatchMapping("/news/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        //DTO -> Entity
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        //target read
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청 응답
            log.info("잘못된 요청! id: {}, article:{}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //업데이트 및 정상 응답(200)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //DELETE
    @DeleteMapping("/news/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if(target == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        //대상 삭제
        articleRepository.delete(target);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
