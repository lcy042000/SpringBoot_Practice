package com.example.springbootFirst.controller;

import com.example.springbootFirst.DTO.ArticleForm;
import com.example.springbootFirst.entity.Article;
import com.example.springbootFirst.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {
    @Autowired //spring boot가 미리 생성해놓은 객체를 가져다가 자동 연결(의존성 주입(DI))
    private ArticleRepository articleRepository;

    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleEntityList = articleRepository.findAll();

        model.addAttribute("articleList", articleEntityList);

        return "articles/index";
    }

    @GetMapping("/articles/new")
    public String newArticleForm(){ // /articles/new page rendering
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm){ // 입력받은 form data를 db에 create
        log.info(articleForm.toString());

        //DTO를 Entity로 변환
        Article article = articleForm.toEntity();
        log.info(article.toString());

        //Repository에게 Entity를 DB에 저장하도록 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        //뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

        //DTO를 Entity로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        //Entity를 DB로 저장
        //DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //기존 데이터가 있다면 값을 갱신
        if(target != null){
            articleRepository.save(articleEntity);
        }

        //수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }
}
